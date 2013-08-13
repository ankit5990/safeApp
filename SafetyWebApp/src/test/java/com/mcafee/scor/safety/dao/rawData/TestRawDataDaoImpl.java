package com.mcafee.scor.safety.dao.rawData;

import org.junit.Test;

import com.mcafee.scor.safety.common.db.CommonDbTest;
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
		RawData obj = new RawData();
		rawDataDao.add(obj);
	}
}
