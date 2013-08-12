package com.mcafee.scor.safety.model;

import java.io.Serializable;

/**
 * +ve value of latitude means north -ve value means south
 * 
 * +ve value of longitude means east and -ve means west
 * @author SAMSUNG
 *
 */
public class Coordinates implements Serializable{
	private static final String SEPERATOR = ":";

	/**
	 * 
	 */
	private static final long serialVersionUID = 2886997068568483520L;
	
	private int latitude;
	private int longitude;
	
	public Coordinates() {
	}
	
	public Coordinates(int latitude, int longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public int getLatitude() {
		return latitude;
	}

	public int getLongitude() {
		return longitude;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + latitude;
		result = prime * result + longitude;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coordinates other = (Coordinates) obj;
		if (latitude != other.latitude)
			return false;
		if (longitude != other.longitude)
			return false;
		return true;
	}

	/**
	 * 
	 * argument coordinatesString should be of the format latitude:longitude
	 * For eg 10:-10, 78,-98
	 * 
	 * @param coordinatesString
	 * @return
	 */
	public static Coordinates getFromString(String coordinatesString) {
		String[] args = coordinatesString.split(SEPERATOR);
		if(args.length != 2){
			throw new Error("Argument stirng not properly formated");
		}
		Coordinates coordinates = new Coordinates();
		coordinates.latitude = Integer.parseInt(args[0]);
		coordinates.longitude = Integer.parseInt(args[1]);
		return coordinates;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(latitude);
		stringBuilder.append(SEPERATOR);
		stringBuilder.append(longitude);
		return stringBuilder.toString();
	}
	
}
