package com.mcafee.scor.safety.model;

import java.util.Calendar;
import java.util.Date;

public enum TimeOfDay {
	MORNING(0),
	AFTERNOON(1),
	EVENING(2),
	NIGHT(3);
	
	private int integerValue;
	
	TimeOfDay(int integerValue){
		this.integerValue = integerValue;
	}
	
	public int getIntegerValue(){
		return integerValue;
	}
	
	public TimeOfDay getByIntegerValue(int val){
		for(TimeOfDay timeOfDay : TimeOfDay.values()){
			if(val == timeOfDay.getIntegerValue()){
				return timeOfDay;
			}
		}
		return null;
	}

	public static TimeOfDay getByTimeMillies(Long timeMillies) {
		Date date = new Date(timeMillies);
		Calendar calendar = Calendar.getInstance();//TODO : should have used with the locale of rawData
		calendar.setTime(date);
		int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
		return getByHourOfDay(hourOfDay);
	}

	private static TimeOfDay getByHourOfDay(int hourOfDay) {
		if(hourOfDay<0 || hourOfDay>24){
			throw new IllegalArgumentException("hour of day must not in the range of 0-24");
		}
		
		if(hourOfDay>=5 && hourOfDay<12){
			return MORNING;
		}
		else if(hourOfDay>=12 && hourOfDay<17){
			return AFTERNOON;
		}
		else if(hourOfDay>=17 && hourOfDay<20){
			return EVENING;
		}
		else if((hourOfDay>=20 && hourOfDay<=24) || (hourOfDay>=0 && hourOfDay<5)){
			return NIGHT;
		}
		
		assert false; //this statement should never have been reached
		
		return null;
	}
}
