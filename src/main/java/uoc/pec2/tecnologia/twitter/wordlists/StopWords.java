package uoc.pec2.tecnologia.twitter.wordlists;

import java.io.IOException;
import java.util.Properties;

public class StopWords {

	public static final StopWords INSTANCE = new StopWords();
	
	private Properties stopWordsList = new Properties();
	
	public static StopWords getInstance() {
		return StopWords.INSTANCE;
	}
	
	private StopWords() {
		try {
			stopWordsList.load(StopWords.class.getResourceAsStream("/stopwords.properties"));
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	public boolean isStopWord(String word) {
		return stopWordsList.containsKey(word);
	}	
}
