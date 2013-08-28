package com.mcafee.scor.safety.model;

public enum Transport {
	PEDESTRIAN(0),
	RICKSHAW(1),
	BUS(2),
	CAB(3),
	PRIVATE_CAR(4);
	
	private int intVal;

	private Transport(int intVal) {
		this.intVal = intVal;
	}
	
	public static Transport getByIntVal(int intVal){
		for(Transport transport : Transport.values()){
			if(transport.getIntVal() == intVal){
				return transport;
			}
		}
		return null;
	}

	public int getIntVal() {
		return intVal;
	}

}
