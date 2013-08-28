package com.mcafee.scor.safety.action;

public enum ActionResponse {
	FAIL(0),
	SUCCESS(1);
	
	int value;
	
	private ActionResponse(int value) {
		this.value = value;
	}
	
	public int getIntValue(){
		return this.value;
	}
}
