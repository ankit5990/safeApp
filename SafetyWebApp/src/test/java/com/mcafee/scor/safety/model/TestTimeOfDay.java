package com.mcafee.scor.safety.model;

import java.util.Calendar;

import org.junit.Test;

import junit.framework.TestCase;

public class TestTimeOfDay extends TestCase{
	
	@Test
	public void testGetByTimeMilliesMorning(){
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(1990, Calendar.SEPTEMBER, 5, 5, 15);
		
		TimeOfDay t = TimeOfDay.getByTimeMillies(calendar.getTimeInMillis());
		
		assertEquals(TimeOfDay.MORNING, t);
	}
	
	@Test
	public void testGetByTimeMilliesAfternoon(){
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(1990, Calendar.SEPTEMBER, 5, 13, 15);
		
		TimeOfDay t = TimeOfDay.getByTimeMillies(calendar.getTimeInMillis());
		
		assertEquals(TimeOfDay.AFTERNOON, t);
	}
	
	@Test
	public void testGetByTimeMillies_evening(){
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(1990, Calendar.SEPTEMBER, 5, 17, 15);
		
		TimeOfDay t = TimeOfDay.getByTimeMillies(calendar.getTimeInMillis());
		
		assertEquals(TimeOfDay.EVENING, t);
	}
	
	@Test
	public void testGetByTimeMillies_night1(){
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(1990, Calendar.SEPTEMBER, 5, 23, 15);
		
		TimeOfDay t = TimeOfDay.getByTimeMillies(calendar.getTimeInMillis());
		
		assertEquals(TimeOfDay.NIGHT, t);
	}
	
	@Test
	public void testGetByTimeMillies_night2(){
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(1990, Calendar.SEPTEMBER, 5, 0, 15);
		
		TimeOfDay t = TimeOfDay.getByTimeMillies(calendar.getTimeInMillis());
		
		assertEquals(TimeOfDay.NIGHT, t);
	}
}
