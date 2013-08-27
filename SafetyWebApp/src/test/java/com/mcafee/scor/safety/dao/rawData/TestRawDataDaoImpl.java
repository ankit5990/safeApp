package com.mcafee.scor.safety.dao.rawData;

import java.util.List;

import org.junit.Test;

import com.mcafee.scor.safety.common.db.CommonDbTest;
import com.mcafee.scor.safety.model.Transport;
import com.mcafee.scor.safety.model.rawData.RawData;

public class TestRawDataDaoImpl extends CommonDbTest{

	RawDataDaoImpl rawDataDao;
	
	@Override
	protected void caseSetup() {
		rawDataDao = new RawDataDaoImpl();
		rawDataDao.setSessionFactory(sessionFactory);
	}

	@Override
	protected void caseTearDown() {

	}


	@Test
	public void testAdd(){
		RawData obj = getSampleObject();
		rawDataDao.add(obj);
		
		int id = obj.getId();
		RawData readObject = rawDataDao.read(RawData.class, id);
		assertEquals("save object not same as that read from db",obj, readObject);
	}

	private RawData getSampleObject() {
		RawData obj = new RawData();
		obj.setLatitude(-1.45);
		obj.setLongitude(1.88);
		obj.setStreetName("my street");
		obj.setTime(System.currentTimeMillis());
		obj.setVictimTransport(Transport.RICKSHAW);
		return obj;
	}
	
	@Test
	public void testReadTopN(){
		rawDataDao.add(getSampleObject());
		rawDataDao.add(getSampleObject());
		rawDataDao.add(getSampleObject());
		
		List<RawData> result = rawDataDao.readTopN(2);
		
		assertEquals(1, result.get(0).getId());
		assertEquals(2, result.get(1).getId());
	}
}
