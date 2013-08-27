package com.mcafee.scor.safety.dao.processedData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

import com.mcafee.scor.safety.dao.CommonBaseDaoImpl;
import com.mcafee.scor.safety.model.Coordinates;
import com.mcafee.scor.safety.model.Rating;
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

	@Override
	public Map<Coordinates, Rating> getRatingAroundCoordinate(Coordinates coordinate, int radius){
		Session session = null;
		Transaction trans = null;
		try{
			session = getSession();
			trans = session.beginTransaction();
			
			String queryString = "From ProcessedData "+ 
				"Where acos(sin(radians(:latitude))*sin(radians(latitude)) + cos(radians(:latitude))*cos(radians(latitude))*cos(radians(longitude)-radians(:longitude))) * 6371 * 1000 < :rad";
			
			Query query = session.createQuery(queryString);
			query.setDouble("latitude", coordinate.getLatitude());
			query.setDouble("longitude", coordinate.getLongitude());
			query.setInteger("rad", radius);
			
			List<ProcessedData> processedDataItems = query.list();
			
			Map<Coordinates, Rating> dataMap = new HashMap<Coordinates, Rating>();
			
			for (ProcessedData processedData : processedDataItems) {
				Coordinates coordinates = new Coordinates(processedData.getLatitude(), processedData.getLongitude());
				dataMap.put(coordinates, processedData.getRating());
			}
			return dataMap;
		}finally{
			commitTransaction(trans);
			closeSession(session);
		}
	}
}
