package com.mcafee.scor.safety.dao.processedData;

import java.util.ArrayList;
import java.util.HashMap;
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
	public void testUpdateCrimeCount_diffTransportSubsequentInsert(){
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
		
		ProcessedData processedData3 = getSampleObject();
		processedData3.setAutoId(3);
		processedData3.setVictimTransport(Transport.CAB);
		
		
		List<ProcessedData> processedDataList = new ArrayList<ProcessedData>();
		processedDataList.add(processedData);
		processedDataList.add(processedData2);
		processedDataList.add(processedData3);
		
		processedDataDao.updateCrimeCount(processedDataList);
		
		processedData.setNumberOfCrimes(prvCount + incrementInCount);
		assertInsertedProperly(processedData);
		assertInsertedProperly(processedData2);
		assertInsertedProperly(processedData3);
	}
	
	@Test
	public void testUpdateCrimeCount_diffTimeOfDaySubsequentInsert(){
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
		
		ProcessedData processedData3 = getSampleObject();
		processedData3.setAutoId(3);
		processedData3.setTimeOfDay(TimeOfDay.MORNING);
		
		
		List<ProcessedData> processedDataList = new ArrayList<ProcessedData>();
		processedDataList.add(processedData);
		processedDataList.add(processedData2);
		processedDataList.add(processedData3);
		
		processedDataDao.updateCrimeCount(processedDataList);
		
		processedData.setNumberOfCrimes(prvCount + incrementInCount);
		assertInsertedProperly(processedData);
		assertInsertedProperly(processedData2);
		assertInsertedProperly(processedData3);
	}
	
	@Test
	public void testGetRatingAroundCoordinate_completeMatch(){
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
		
		Map<Coordinates, Rating> expected = new HashMap<Coordinates, Rating>();
		expected.put(new Coordinates(latitude, longitude), Rating.RED);
		assertEquals(expected, result);
	}
	
	@Test
	public void testGetRatingAroundCoordinate_closeMatch(){
		ProcessedData sampleObject = getSampleObject();
		double latitude = sampleObject.getLatitude();
		double longitude = sampleObject.getLongitude();
		int radius = 20;
		
		processedDataDao.add(sampleObject);
		
		sampleObject.setAutoId(2);
		sampleObject.setLatitude(sampleObject.getLatitude() + 10);
		sampleObject.setLongitude(sampleObject.getLongitude() + 10);
		sampleObject.setStreetName(sampleObject.getStreetName()+"1");
		processedDataDao.add(sampleObject);
		
		Coordinates coordinate = new Coordinates(latitude + 0.0001,longitude + 0.0001);
		Map<Coordinates, Rating> result = processedDataDao.getRatingAroundCoordinate(coordinate, TimeOfDay.EVENING, Transport.PRIVATE_CAR, radius);
		
		Map<Coordinates, Rating> expected = new HashMap<Coordinates, Rating>();
		expected.put(new Coordinates(latitude, longitude), Rating.RED);
		assertEquals(expected, result);
	}
	
	@Test
	public void testGetRatingAroundCoordinate_multipleCloseMatch(){
		ProcessedData sampleObject = getSampleObject();
		double latitude = sampleObject.getLatitude();
		double longitude = sampleObject.getLongitude();
		int radius = 20;
		
		processedDataDao.add(sampleObject);
		
		sampleObject.setAutoId(2);
		sampleObject.setLatitude(latitude + 10);
		sampleObject.setLongitude(longitude + 10);
		sampleObject.setStreetName(sampleObject.getStreetName()+"1");
		processedDataDao.add(sampleObject);
		
		sampleObject.setAutoId(3);
		sampleObject.setLatitude(latitude + 0.00005);
		sampleObject.setLongitude(longitude + 0.00005);
		sampleObject.setStreetName(sampleObject.getStreetName()+"2");
		sampleObject.setRating(Rating.GREEN);
		processedDataDao.add(sampleObject);
		
		Coordinates coordinate = new Coordinates(latitude + 0.0001,longitude + 0.0001);
		Map<Coordinates, Rating> result = processedDataDao.getRatingAroundCoordinate(coordinate, TimeOfDay.EVENING, Transport.PRIVATE_CAR, radius);
		
		Map<Coordinates, Rating> expected = new HashMap<Coordinates, Rating>();
		expected.put(new Coordinates(latitude, longitude), Rating.RED);
		expected.put(new Coordinates(latitude + 0.00005,longitude + 0.00005), Rating.GREEN);
		
		
		assertEquals(expected, result);
	}
	
	@Test
	public void testGetRatingForCoordinate(){
		ProcessedData sampleObject = getSampleObject();
		double latitude = sampleObject.getLatitude();
		double longitude = sampleObject.getLongitude();
		
		processedDataDao.add(sampleObject);
		
		sampleObject.setAutoId(2);
		sampleObject.setLatitude(sampleObject.getLatitude() + 10);
		sampleObject.setLongitude(sampleObject.getLongitude() + 10);
		sampleObject.setStreetName(sampleObject.getStreetName()+"1");
		sampleObject.setRating(Rating.GREEN);
		processedDataDao.add(sampleObject);
		
		Coordinates coordinate = new Coordinates(latitude+0.001,longitude);
		Rating result = processedDataDao.getRatingForCoordinate(coordinate);
		
		assertEquals(Rating.RED, result);
	}
	
	@Test
	public void testReadTopN(){
		processedDataDao.add(getSampleObject());
		processedDataDao.add(getSampleObject());
		processedDataDao.add(getSampleObject());
		
		List<ProcessedData> result = processedDataDao.readTopN(2);
		
		assertEquals(2, result.size());
		assertEquals(1, result.get(0).getAutoId());
		assertEquals(2, result.get(1).getAutoId());
	}
	
	@Test
	public void testReadRange(){
		processedDataDao.add(getSampleObject());
		processedDataDao.add(getSampleObject());
		processedDataDao.add(getSampleObject());
		processedDataDao.add(getSampleObject());
		processedDataDao.add(getSampleObject());
		
		List<ProcessedData> result = processedDataDao.readRange(2, 4);
		
		assertEquals(3, result.size());
		assertEquals(2, result.get(0).getAutoId());
		assertEquals(3, result.get(1).getAutoId());
		assertEquals(4, result.get(2).getAutoId());
	}
}
