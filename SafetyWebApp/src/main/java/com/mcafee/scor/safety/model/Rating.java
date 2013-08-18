package com.mcafee.scor.safety.model;


public enum Rating {
	UNRATED(0),
	RED(1),
	YELLOW(2),
	GREEN(3);
	
	private int integerValue;
	
	private Rating(int integerValue){
		this.integerValue = integerValue;
	}
	
	public Rating getByIntegerValue(int val){
		for(Rating rating : Rating.values()){
			if(rating.integerValue == val){
				return rating;
			}
		}
		return UNRATED;
	}
	
	public int getIntegerValue(){
		return this.integerValue;
	}
}
