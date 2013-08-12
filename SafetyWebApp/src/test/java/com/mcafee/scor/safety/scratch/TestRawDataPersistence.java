package com.mcafee.scor.safety.scratch;

import java.util.List;

import org.hibernate.Session;
import org.junit.Test;

import com.mcafee.scor.safety.common.db.CommonDbTest;
import com.mcafee.scor.safety.model.Coordinates;
import com.mcafee.scor.safety.model.Transport;
import com.mcafee.scor.safety.model.rawData.RawData;

public class TestRawDataPersistence extends CommonDbTest{
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
			rawData.setCoordinates(new Coordinates(-41, -34));
			rawData.setStreetName("streetName");
			rawData.setTime(System.currentTimeMillis());
			rawData.setVictimTransport(Transport.PRIVATE_CAR);
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
