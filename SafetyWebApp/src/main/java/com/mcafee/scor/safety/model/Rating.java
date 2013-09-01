package com.mcafee.scor.safety.model;


public enum Rating {
	GRAY(0),// gray is for unrated
	RED(1),
	YELLOW(2),
	GREEN(3);
	
	private int integerValue;
	
	private Rating(int integerValue){
		this.integerValue = integerValue;
	}
	
	public static Rating getByIntegerValue(int val){
		for(Rating rating : Rating.values()){
			if(rating.integerValue == val){
				return rating;
			}
		}
		return GRAY;
	}
	
	public int getIntegerValue(){
		return this.integerValue;
	}
}
