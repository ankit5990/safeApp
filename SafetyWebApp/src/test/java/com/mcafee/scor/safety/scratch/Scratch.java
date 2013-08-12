package com.mcafee.scor.safety.scratch;

import java.util.List;

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
		try{
			session.beginTransaction();
			RawData rawData = new RawData();
			rawData.setCoordinates(new Coordinates(1, -1));
			rawData.setNumberOfCrimes(21);
			rawData.setTimeOfDay(TimeOfDay.AFTERNOON);
			session.save(rawData);
			session.getTransaction().commit();
		}finally{
			session.flush();
		}
	}
	
	@Test
	public void testRead(){
		try{
			session.beginTransaction();
			List<RawData> rawDataList = session.createQuery("from RawData").list();
			System.out.println("size of raw data list = "+rawDataList.size());
			for(RawData data : rawDataList){
				System.out.println(data);
			}
		}finally{
			session.close();
		}
	}
	
	@Override
	protected void caseTearDown() {
		if(session!=null && session.isOpen()){
			session.close();
		}
	}
}
