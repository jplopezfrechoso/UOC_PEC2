package uoc.pec2.tecnologia.twitter.functions;

import org.apache.spark.api.java.function.Function;

import twitter4j.HashtagEntity;
import twitter4j.Status;
import twitter4j.UserMentionEntity;
import uoc.pec2.tecnologia.twitter.MessageData;
import uoc.pec2.tecnologia.twitter.MessageLocation;

public class TwitterDataMapper implements Function<Status, MessageData> {

	private static final long serialVersionUID = 1L;

	@Override
	public MessageData call(Status s) throws Exception {
		
    	MessageData messageData = new MessageData();
    	
    	messageData.setId(s.getId());
    	messageData.setLanguage(s.getLang());
    	
    	if(s.getRetweetedStatus() != null) {
    		messageData.setMessage(s.getRetweetedStatus().getText());
    	} else {
    		messageData.setMessage(s.getText());
    	}
    	
    	if(s.getGeoLocation() != null) {
    		MessageLocation messageLocation = new MessageLocation(s.getGeoLocation().getLatitude(), 
    											  s.getGeoLocation().getLongitude());
    		messageData.setLocation(messageLocation);
    	} else {
    		if(s.getUser().getLocation() != null) {
    			System.out.println(s.getUser().getLocation());
    		}
    		
    	}
    	
    	for(HashtagEntity hashtag : s.getHashtagEntities()) {
    		messageData.getHashtags().add(hashtag.getText());
    	}
    	
    	for(UserMentionEntity mention : s.getUserMentionEntities()) {
    		messageData.getMentions().add(mention.getScreenName());
    	}
    	
    	return messageData;
	}

}
