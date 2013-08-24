package com.mcafee.scor.safety.dao.processedData;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

import com.mcafee.scor.safety.dao.CommonBaseDaoImpl;
import com.mcafee.scor.safety.model.processedData.ProcessedData;

public class ProcessedDataDaoImpl extends CommonBaseDaoImpl<ProcessedData> implements ProcessedDataDao{

	@Override
	public void updateCrimeCount(List<ProcessedData> processedDataList) {
		Session session = null;
		Transaction trans = null;
		try{
			session = getSession();
			trans = session.beginTransaction();

			int [] result = new int[processedDataList.size()];
			for (int index = 0; index < result.length; index++) {
				ProcessedData processedData = processedDataList.get(index);
				Query query = session.createQuery("update ProcessedData set numberOfCrimes=numberOfCrimes+? where streetName=?");
				query.setInteger(0, processedData.getNumberOfCrimes());
				query.setString(1, processedData.getStreetName());
				result[index] = query.executeUpdate();
			}
			
			for (int i = 0; i < result.length; i++) {
				ProcessedData processedData = processedDataList.get(i);
				if(result[i] <= 0){
					session.save(processedData);
				}
			}
		}finally{
			commitTransaction(trans);
			closeSession(session);
		}
	}

}
