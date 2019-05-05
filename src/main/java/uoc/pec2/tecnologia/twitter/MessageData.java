package uoc.pec2.tecnologia.twitter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MessageData implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Message id
	 */
	private long id;
	
	/**
	 * Message text
	 */
	private String message;
	
	private MessageLocation location;
	
	private List<String> hashtags = new ArrayList<String>();
	
	private List<String> mentions = new ArrayList<String>();
	
	/**
	 * Message language
	 */
	private String language;

	public MessageData(final long id, final String language, final String message, final MessageLocation location) {
		this.id = id;
		this.language = language;
		this.message = message;
		this.location = location;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public MessageData() {
		// Do nothing
	}
		
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public MessageLocation getLocation() {
		return location;
	}

	public void setLocation(MessageLocation location) {
		this.location = location;
	}

	public List<String> getHashtags() {
		return hashtags;
	}

	public void setHashtags(List<String> hashtags) {
		this.hashtags = hashtags;
	}
	
	public List<String> getMentions() {
		return mentions;
	}

	public void setMentions(List<String> mentions) {
		this.mentions = mentions;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FlatData [id=");
		builder.append(id);
		builder.append(", message=");
		builder.append(message);
		builder.append(", language=");
		builder.append(language);
		builder.append("]");
		return builder.toString();
	}

}
