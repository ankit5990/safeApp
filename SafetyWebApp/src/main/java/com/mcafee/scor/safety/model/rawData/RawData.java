package com.mcafee.scor.safety.model.rawData;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.mcafee.scor.safety.model.Coordinates;
import com.mcafee.scor.safety.model.TimeOfDay;

@Entity
@Table( name="raw_data" )
public class RawData {
 	private int id;
	private Coordinates coordinates;
	private int numberOfCrimes;
	private TimeOfDay timeOfDay;
	
	public RawData() {
	}
	
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy="increment")
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
