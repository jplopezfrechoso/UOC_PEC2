package uoc.pec2.tecnologia.twitter.functions;

import org.apache.spark.api.java.function.Function;

import twitter4j.Status;
import twitter4j.UserMentionEntity;

public class UserMentionedFilter implements Function<Status,Boolean> {

	private static final long serialVersionUID = 1L;
	private String user;
	
	public UserMentionedFilter(final String user) {
		this.user = user;
	}
	
	@Override
	public Boolean call(final Status s) throws Exception {	
		boolean isUserMentioned = false;
    	
    	UserMentionEntity[] userMentions =  s.getUserMentionEntities();
    	
    	if(userMentions.length > 0) {
    		for(UserMentionEntity userMention : userMentions) {
    			if(userMention.getScreenName().equalsIgnoreCase(user)) {
    				isUserMentioned = true;
    				break;
    			}
    		}
    	}
    	return isUserMentioned;
	}

}
