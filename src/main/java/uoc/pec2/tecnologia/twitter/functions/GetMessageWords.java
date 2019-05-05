package uoc.pec2.tecnologia.twitter.functions;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.spark.api.java.function.FlatMapFunction;
import org.springframework.stereotype.Component;

import uoc.pec2.tecnologia.twitter.MessageData;
import uoc.pec2.tecnologia.twitter.wordlists.StopWords;
import uoc.pec2.tecnologia.web.beans.Word;

public class GetMessageWords implements FlatMapFunction<MessageData, String>{

	private static final long serialVersionUID = 1L;

	@Override
	public Iterator<String> call(MessageData d) throws Exception {
    	List<String> words = new ArrayList<>(); 
    	
    	String text = d.getMessage();
    	
    	String textLower = text.toLowerCase();
    	
    	BreakIterator breakIterator = BreakIterator.getWordInstance();
        breakIterator.setText(textLower);
        
        int lastIndex = breakIterator.first();
        while (BreakIterator.DONE != lastIndex) {
            int firstIndex = lastIndex;
            lastIndex = breakIterator.next();
            if (lastIndex != BreakIterator.DONE && Character.isLetterOrDigit(textLower.charAt(firstIndex))) {
            	String word = textLower.substring(firstIndex, lastIndex);
            	if(!StopWords.getInstance().isStopWord(word)) {
            		words.add(word);
            	}
            }
        }
    	return words.iterator();
	}

}
