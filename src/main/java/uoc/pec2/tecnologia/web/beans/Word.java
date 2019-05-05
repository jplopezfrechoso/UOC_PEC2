package uoc.pec2.tecnologia.web.beans;

import java.io.Serializable;

public class Word implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String text;
	
	private Long weight;
		
	public Word() {
		// Do nothing
	}
	
	public Word(String text, Long weight ) {
		this.text = text;
		this.weight = weight;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public Long getWeight() {
		return weight;
	}
	
	public void setWeight(Long weight) {
		this.weight = weight;
	}

}
