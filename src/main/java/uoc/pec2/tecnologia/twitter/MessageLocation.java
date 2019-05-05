package uoc.pec2.tecnologia.twitter;

import java.io.Serializable;

public class MessageLocation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MessageLocation(final double latitude, final double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MessageLocation [latitude=");
		builder.append(latitude);
		builder.append(", longitude=");
		builder.append(longitude);
		builder.append("]");
		return builder.toString();
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	private double latitude;
	
	private double longitude;
}
