package uoc.pec2.tecnologia.twitter.functions;

import org.apache.spark.api.java.function.Function;

import twitter4j.HashtagEntity;
import twitter4j.Status;

public class HashtagFilter implements Function<Status,Boolean> {

	private static final long serialVersionUID = 1L;

	private String hashtag;
	
	public HashtagFilter(final String hashtag) {
		this.hashtag = hashtag;
	}
	
	@Override
	public Boolean call(Status s) throws Exception {
		boolean isHashTagUsed = false;
		
		if(s.getLang().equals("es")) {
			if(!"NO_TAG_FILTER".equals(this.hashtag)) {
				HashtagEntity[] hashtags = s.getHashtagEntities();
				
				if(hashtags.length > 0) {
		    		for(HashtagEntity hashtag : hashtags) {
		    			if(hashtag.getText().equalsIgnoreCase(this.hashtag)) {
		    				isHashTagUsed = true;
		    				break;
		    			}
		    		}
		    	}
			} else {
				isHashTagUsed = true;
			}
		}
		return isHashTagUsed;
	}
}
