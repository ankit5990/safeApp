package com.mcafee.scor.safety.dao.rawData;

import org.junit.Test;

import com.mcafee.scor.safety.common.db.CommonDbTest;
import com.mcafee.scor.safety.model.Coordinates;
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
		obj.setCoordinates(new Coordinates(-1, +1));
		obj.setStreetName("my street");
		obj.setTime(System.currentTimeMillis());
		obj.setVictimTransport(Transport.RICKSHAW);
		return obj;
	}
	
}
