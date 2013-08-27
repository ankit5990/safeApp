package com.mcafee.scor.safety.model.processedData;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mcafee.scor.safety.model.Rating;
import com.mcafee.scor.safety.model.TimeOfDay;
import com.mcafee.scor.safety.model.Transport;

/**
 * @author saurabhb
 *
 */
@Entity
@Table(name=ProcessedData.TABLE_NAME)
public class ProcessedData implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5001840414001132956L;
	
	public static final String TABLE_NAME="processed_data";
	
	private int autoId;
	private String streetName;
	private double latitude;
	private double longitude;
	private TimeOfDay timeOfDay;
	private Transport victimTransport;
	private int numberOfCrimes;
	private Rating rating = Rating.UNRATED;	//assigning default value for rating for schema
	
	
	public ProcessedData(ProcessedData processedData) {
		this.autoId = processedData.autoId;
		this.latitude = processedData.latitude;
		this.longitude = processedData.longitude;
		this.numberOfCrimes = processedData.numberOfCrimes;
		this.rating = processedData.rating;
		this.streetName = processedData.streetName;
		this.timeOfDay = processedData.timeOfDay;
		this.victimTransport = processedData.victimTransport;
	}
	
	public ProcessedData() {
		// default constructor
	}
	
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy="increment")
	public int getAutoId() {
		return autoId;
	}
	public void setAutoId(int autoId) {
		this.autoId = autoId;
	}
	public String getStreetName() {
		return streetName;
	}
	public void setStreetName(String streetName) {
		this.streetName = streetName;
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

	public TimeOfDay getTimeOfDay() {
		return timeOfDay;
	}
	public void setTimeOfDay(TimeOfDay timeOfDay) {
		this.timeOfDay = timeOfDay;
	}
	public Transport getVictimTransport() {
		return victimTransport;
	}
	public void setVictimTransport(Transport victimTransport) {
		this.victimTransport = victimTransport;
	}
	public int getNumberOfCrimes() {
		return numberOfCrimes;
	}
	public void setNumberOfCrimes(int numberOfCrimes) {
		this.numberOfCrimes = numberOfCrimes;
	}
	public Rating getRating() {
		return rating;
	}
	public void setRating(Rating rating) {
		this.rating = rating;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + autoId;
		long temp;
		temp = Double.doubleToLongBits(latitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + numberOfCrimes;
		result = prime * result + ((rating == null) ? 0 : rating.hashCode());
		result = prime * result
				+ ((streetName == null) ? 0 : streetName.hashCode());
		result = prime * result
				+ ((timeOfDay == null) ? 0 : timeOfDay.hashCode());
		result = prime * result
				+ ((victimTransport == null) ? 0 : victimTransport.hashCode());
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
		ProcessedData other = (ProcessedData) obj;
		if (autoId != other.autoId)
			return false;
		if (Double.doubleToLongBits(latitude) != Double
				.doubleToLongBits(other.latitude))
			return false;
		if (Double.doubleToLongBits(longitude) != Double
				.doubleToLongBits(other.longitude))
			return false;
		if (numberOfCrimes != other.numberOfCrimes)
			return false;
		if (rating != other.rating)
			return false;
		if (streetName == null) {
			if (other.streetName != null)
				return false;
		} else if (!streetName.equals(other.streetName))
			return false;
		if (timeOfDay != other.timeOfDay)
			return false;
		if (victimTransport != other.victimTransport)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProcessedData [autoId=" + autoId + ", streetName=" + streetName
				+ ", latitude=" + latitude + ", longitude=" + longitude
				+ ", timeOfDay=" + timeOfDay + ", victimTransport="
				+ victimTransport + ", numberOfCrimes=" + numberOfCrimes
				+ ", rating=" + rating + "]";
	}

}
