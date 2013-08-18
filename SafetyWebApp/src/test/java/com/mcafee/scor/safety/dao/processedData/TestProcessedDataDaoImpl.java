package com.mcafee.scor.safety.dao.processedData;

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
		obj.setCoordinates(new Coordinates(-82, 39));
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

}
