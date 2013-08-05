package com.mcafee.scor.safety.model;

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
}
