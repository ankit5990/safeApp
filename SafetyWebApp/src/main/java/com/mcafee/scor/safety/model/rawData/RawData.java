package com.mcafee.scor.safety.model.rawData;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mcafee.scor.safety.model.Transport;

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
	private String streetName;
	private double latitude;
	private double longitude;
	private Long time;
	private Transport victimTransport;
	
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

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public Transport getVictimTransport() {
		return victimTransport;
	}

	public void setVictimTransport(Transport victimTransport) {
		this.victimTransport = victimTransport;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		long temp;
		temp = Double.doubleToLongBits(latitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((streetName == null) ? 0 : streetName.hashCode());
		result = prime * result + ((time == null) ? 0 : time.hashCode());
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
		RawData other = (RawData) obj;
		if (id != other.id)
			return false;
		if (Double.doubleToLongBits(latitude) != Double
				.doubleToLongBits(other.latitude))
			return false;
		if (Double.doubleToLongBits(longitude) != Double
				.doubleToLongBits(other.longitude))
			return false;
		if (streetName == null) {
			if (other.streetName != null)
				return false;
		} else if (!streetName.equals(other.streetName))
			return false;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		if (victimTransport != other.victimTransport)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RawData [id=" + id + ", streetName=" + streetName
				+ ", latitude=" + latitude + ", longitude=" + longitude
				+ ", time=" + time + ", victimTransport=" + victimTransport
				+ "]";
	}

}
