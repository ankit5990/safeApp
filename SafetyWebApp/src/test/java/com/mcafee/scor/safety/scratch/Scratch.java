package com.mcafee.scor.safety.scratch;

import org.hibernate.Session;
import org.junit.Test;

import com.mcafee.scor.safety.common.db.CommonDbTest;
import com.mcafee.scor.safety.model.Coordinates;
import com.mcafee.scor.safety.model.TimeOfDay;
import com.mcafee.scor.safety.model.rawData.RawData;

public class Scratch extends CommonDbTest{
	private Session session;
	
	
	@Override
	protected void caseSetup() {
		session = sessionFactory.openSession();
	}
	
	@Test
	public void testSave() throws Exception {
		session.beginTransaction();
		RawData rawData = new RawData();
		rawData.setCoordinates(new Coordinates(1, -1));
		rawData.setNumberOfCrimes(21);
		rawData.setTimeOfDay(TimeOfDay.AFTERNOON);
		session.save(rawData);
		session.getTransaction().commit();
		session.close();
	}
	
	@Override
	protected void caseTearDown() {
		if(session!=null && session.isOpen()){
			session.close();
		}
	}
}
