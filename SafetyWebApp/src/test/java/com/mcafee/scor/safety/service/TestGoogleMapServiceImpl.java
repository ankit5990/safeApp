package com.mcafee.scor.safety.service;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import com.mcafee.scor.safety.model.Coordinates;
import com.mcafee.scor.safety.model.TimeOfDay;
import com.mcafee.scor.safety.model.Transport;

public class TestGoogleMapServiceImpl extends TestCase{
	private GoogleMapServiceImpl googleMapService;
	
	@Override
	public void setUp() throws Exception {
		googleMapService = new GoogleMapServiceImpl();
	}
	
	
	@Test
	public void testGetRatedPath(){
		Coordinates start = new Coordinates(28.6100,77.2300);
		Coordinates end = new Coordinates(27.6100,78.2300);
		TimeOfDay timeOfDay = TimeOfDay.MORNING;
		Transport transport = Transport.BUS;
		List<RatedCoordinate> ratedCoordinates = googleMapService.getRatedPath(start, end, timeOfDay, transport);
		System.out.println(Arrays.toString(ratedCoordinates.toArray()));
	}
}
