package com.mcafee.scor.safety.model.rawData;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.mcafee.scor.safety.model.Coordinates;
import com.mcafee.scor.safety.model.TimeOfDay;

/**
 * @author SAMSUNG
 *
 */
/**
 * @author SAMSUNG
 *
 */
@Entity
@Table( name=RawData.TABLE_NAME )
public class RawData {
	public static final String TABLE_NAME = "raw_data";
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
	
	@Transient
	public Coordinates getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
	}
	public String getCoordinatesString() {
		return coordinates.toString();
	}
	public void setCoordinatesString(String coordinates) {
		this.coordinates = Coordinates.getFromString(coordinates);
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

	@Override
	public String toString() {
		return "RawData [id=" + id + ", coordinates=" + coordinates
				+ ", numberOfCrimes=" + numberOfCrimes + ", timeOfDay="
				+ timeOfDay + "]";
	}
	
}
