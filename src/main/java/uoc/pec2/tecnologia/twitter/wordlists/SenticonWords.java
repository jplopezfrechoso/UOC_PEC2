package uoc.pec2.tecnologia.twitter.wordlists;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SenticonWords {

	private static final SenticonWords INSTANCE = new SenticonWords();
	
	private static class Word {
		
		private String text;
		
		private Double weight;

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public Double getValue() {
			return weight;
		}

		public void setValue(Double value) {
			this.weight = value;
		}
	}
	
	private static final Word DEFAULT = new Word() {
		{
			setValue(0.01);
			setText("NOWORD");
		}
	};
	
	private static class SenticonParser extends DefaultHandler {
		
		private Stack<Word> stack = new Stack<>();
		
		@Override
	    public void startElement (String uri, String localName, String qName, Attributes attributes) throws SAXException {
	    	if(localName.equals("lemma")) {
	    		Word word = new Word();
	    		word.setValue(Double.valueOf(attributes.getValue("pol")));
	    		this.stack.add(word);
	    	}
		}
		
	    @Override
	    public void characters (char ch[], int start, int length) throws SAXException  {
	    	String word = new String(ch, start, length).trim();
	    	if(word.length() > 0) {
	    		this.stack.peek().setText(word.trim());
	    	}
	    }
	    
		public Map<String, Word> getAsMap() {
			Map<String, Word> wordsMap = new HashMap<>();
			
			this.stack.forEach(w -> {
				wordsMap.put(w.getText().toLowerCase(),w);
			});
			
			return wordsMap;
		}
	}

	private Map<String, Word> sentimentMap;
	
	private SenticonWords() {
		
		try {
			SAXParserFactory spf = SAXParserFactory.newInstance();
		    spf.setNamespaceAware(true);
		    SAXParser saxParser = spf.newSAXParser();
		    SenticonParser parser = new SenticonParser();
		    saxParser.parse(SenticonWords.class.getResourceAsStream("/senticon.es.xml"), parser);
		    
		    this.sentimentMap = parser.getAsMap();
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public static SenticonWords getInstance() {
		return SenticonWords.INSTANCE;
	}
	
	public Double evaluate(String word) {
		return this.sentimentMap.getOrDefault(word.toLowerCase(), SenticonWords.DEFAULT).getValue();
	}
}
