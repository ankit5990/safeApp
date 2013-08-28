package com.mcafee.scor.safety.service;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import com.mcafee.scor.safety.model.Coordinates;
import com.mcafee.scor.safety.model.TimeOfDay;
import com.mcafee.scor.safety.model.Transport;

public class TestGoogleMapServiceImpl extends TestCase{
	private GoogleMapServiceImpl googleMapService;
	
	@Before
	public void setup(){
		googleMapService = new GoogleMapServiceImpl();
	}
	
	@Test
	public void testGetRatedPath(){
		Coordinates start = new Coordinates(1,2);
		Coordinates end = new Coordinates(3,4);
		TimeOfDay timeOfDay = TimeOfDay.MORNING;
		Transport transport = Transport.BUS;
		googleMapService.getRatedPath(start, end, timeOfDay, transport);
	}
}
