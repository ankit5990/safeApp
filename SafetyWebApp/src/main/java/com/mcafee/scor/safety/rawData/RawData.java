package com.mcafee.scor.safety.rawData;

import com.mcafee.scor.safety.model.Coordinates;
import com.mcafee.scor.safety.model.TimeOfDay;

public class RawData {
 	private int id;
	private Coordinates coordinates;
	private int numberOfCrimes;
	private TimeOfDay timeOfDay;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Coordinates getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
	}
	public int getNumberOfCrimes() {
		return numberOfCrimes;
	}
	public void setNumberOfCrimes(int numberOfCrimes) {
		this.numberOfCrimes = numberOfCrimes;
	}
	public TimeOfDay getTimeOfDay() {
		return timeOfDay;
	}
	public void setTimeOfDay(TimeOfDay timeOfDay) {
		this.timeOfDay = timeOfDay;
	}
	
	
}
