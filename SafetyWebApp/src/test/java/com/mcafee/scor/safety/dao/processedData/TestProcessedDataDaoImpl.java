package com.mcafee.scor.safety.dao.processedData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.mcafee.scor.safety.common.db.CommonDbTest;
import com.mcafee.scor.safety.model.Coordinates;
import com.mcafee.scor.safety.model.Rating;
import com.mcafee.scor.safety.model.TimeOfDay;
import com.mcafee.scor.safety.model.Transport;
import com.mcafee.scor.safety.model.processedData.ProcessedData;

public class TestProcessedDataDaoImpl extends CommonDbTest{

	private ProcessedDataDaoImpl processedDataDao; 
	
	@Override
	protected void caseSetup() {
		processedDataDao = new ProcessedDataDaoImpl();
		processedDataDao.setSessionFactory(getSessionFactory());
	}

	@Override
	protected void caseTearDown() {
		// TODO Auto-generated method stub
		
	}
	
	@Test
	public void testAdd(){
		ProcessedData processedData = getSampleObject();
		processedDataDao.add(processedData);
		
		assertInsertedProperly(processedData);
	}

	private void assertInsertedProperly(ProcessedData processedData) {
		int id = processedData.getAutoId();
		ProcessedData readObject = processedDataDao.read(ProcessedData.class, id);
		assertEquals("Read object not same as object saved",processedData, readObject);
	}
	
	private ProcessedData getSampleObject() {
		ProcessedData obj = new ProcessedData();
		obj.setAutoId(1);
		obj.setLatitude(-82);
		obj.setLongitude(39);
		obj.setNumberOfCrimes(34);
		obj.setRating(Rating.RED);
		obj.setStreetName("myStreet");
		obj.setTimeOfDay(TimeOfDay.EVENING);
		obj.setVictimTransport(Transport.PRIVATE_CAR);
		return obj;
	}
	
	@Test
	public void testUpdate(){
		ProcessedData processedData = getSampleObject();
		processedDataDao.add(processedData);
		
		processedData.setNumberOfCrimes(processedData.getNumberOfCrimes() + 10);
		
		processedDataDao.update(processedData);
		assertInsertedProperly(processedData);
	}
	
	@Test
	public void testAddOrUpdate(){
		ProcessedData processedData = getSampleObject();
		processedDataDao.addOrUpdate(processedData);
		assertInsertedProperly(processedData);
		
		processedData.setNumberOfCrimes(processedData.getNumberOfCrimes() + 10);
		
		processedDataDao.addOrUpdate(processedData);
		assertInsertedProperly(processedData);
	}

	@Test
	public void testUpdateCrimeCount(){
		ProcessedData processedData = getSampleObject();
		processedDataDao.add(processedData);
		
		int prvCount = processedData.getNumberOfCrimes();
		int incrementInCount = 10;
		
		processedData.setNumberOfCrimes(incrementInCount);
		
		
		ProcessedData processedData2 = getSampleObject();
		processedData2.setAutoId(2);
		processedData2.setStreetName("streetName"+2);
		processedData2.setLatitude(2);
		processedData2.setLongitude(-45);
		processedData2.setNumberOfCrimes(2);
		
		List<ProcessedData> processedDataList = new ArrayList<ProcessedData>();
		processedDataList.add(processedData);
		processedDataList.add(processedData2);
		
		processedDataDao.updateCrimeCount(processedDataList);
		
		processedData.setNumberOfCrimes(prvCount + incrementInCount);
		assertInsertedProperly(processedData);
		assertInsertedProperly(processedData2);
	}

	@Test
	public void testGetRatingAroundCoordinate(){
		ProcessedData sampleObject = getSampleObject();
		double latitude = sampleObject.getLatitude();
		double longitude = sampleObject.getLongitude();
		int radius = 10;
		
		processedDataDao.add(sampleObject);
		
		sampleObject.setAutoId(2);
		sampleObject.setLatitude(sampleObject.getLatitude() + 10);
		sampleObject.setLongitude(sampleObject.getLongitude() + 10);
		sampleObject.setStreetName(sampleObject.getStreetName()+"1");
		processedDataDao.add(sampleObject);
		
		Coordinates coordinate = new Coordinates(latitude,longitude);
		Map<Coordinates, Rating> result = processedDataDao.getRatingAroundCoordinate(coordinate, TimeOfDay.EVENING, Transport.PRIVATE_CAR, radius);
		
		System.err.println(result);
	}
}
