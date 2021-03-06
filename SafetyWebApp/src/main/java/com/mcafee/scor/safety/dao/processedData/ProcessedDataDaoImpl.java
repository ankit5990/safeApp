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
import com.mcafee.scor.safety.model.TimeOfDay;
import com.mcafee.scor.safety.model.Transport;
import com.mcafee.scor.safety.model.processedData.ProcessedData;

public class ProcessedDataDaoImpl extends CommonBaseDaoImpl<ProcessedData> implements ProcessedDataDao{

	private static final double TOLERANCE = 0.0001;

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
				Query query = session.createQuery("update ProcessedData set numberOfCrimes=numberOfCrimes+:numberOfCrimes "
						+ "where streetName=:streetName and timeOfDay=:timeOfDay and victimTransport=:victimTransport");
				query.setInteger("numberOfCrimes", processedData.getNumberOfCrimes());
				query.setString("streetName", processedData.getStreetName());
				query.setInteger("timeOfDay", processedData.getTimeOfDay().getIntegerValue());
				query.setInteger("victimTransport", processedData.getVictimTransport().getIntVal());
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
	public Map<Coordinates, Rating> getRatingAroundCoordinate(Coordinates coordinate,
			TimeOfDay timeOfDay, Transport transport, int radius){
		Session session = null;
		Transaction trans = null;
		try{
			session = getSession();
			trans = session.beginTransaction();
			
			String queryString = "From ProcessedData "+ 
				"Where timeOfDay=:timeOfDay and victimTransport=:victimTransport "
				+ "and  6371 * 1000 * sqrt( pow(radians( latitude - :latitude ), 2) + pow(radians( longitude - :longitude ), 2) ) < :rad";
			
			Query query = session.createQuery(queryString);
			query.setDouble("latitude", coordinate.getLatitude());
			query.setDouble("longitude", coordinate.getLongitude());
			query.setInteger("rad", radius);
			query.setInteger("timeOfDay", timeOfDay.getIntegerValue());
			query.setInteger("victimTransport", transport.getIntVal());
			
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
	
	@Override
	public Rating getRatingForCoordinate(Coordinates coordinate){
		Session session = null;
		Transaction trans = null;
		try{
			session = getSession();
			trans = session.beginTransaction();
			
			String queryString = "from ProcessedData where latitude between :latitude - :tolerance and :latitude + :tolerance"
					+ " and longitude between :longitude - :tolerance and :longitude + :tolerance";
			Query query = session.createQuery(queryString);
			query.setDouble("latitude", coordinate.getLatitude());
			query.setDouble("longitude", coordinate.getLongitude());
			query.setDouble("tolerance", TOLERANCE);
			
			List<ProcessedData> dataList = query.list();
			
			if(dataList == null || dataList.size()==0){
				return Rating.GRAY;
			}
			
			return dataList.get(0).getRating();
		}finally{
			commitTransaction(trans);
			closeSession(session);
		}
	}
	
	@Override
	public List<ProcessedData> readTopN(int lim){
		return readList("ProcessedData", lim);
	}
	
	@Override
	public List<ProcessedData> readRange(int start, int end){
		Session session = null;
		Transaction trans = null;
		try{
			session = getSession();
			trans = session.beginTransaction();
			
			String queryString = "from ProcessedData where id between :start and :end";
			Query query = session.createQuery(queryString);
			query.setInteger("start", start);
			query.setInteger("end", end);
			
			return query.list();
		}finally{
			commitTransaction(trans);
			closeSession(session);
		}
	}
}


