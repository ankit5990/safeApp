package com.mcafee.scor.safety.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mcafee.scor.safety.common.Commonutilities;
import com.mcafee.scor.safety.dao.processedData.ProcessedDataDao;
import com.mcafee.scor.safety.model.Coordinates;
import com.mcafee.scor.safety.model.TimeOfDay;
import com.mcafee.scor.safety.model.Transport;



public class GoogleMapServiceImpl implements GoogleMapService{
	private static final Logger logger = Logger.getLogger(GoogleMapServiceImpl.class);
	
	private static final String DEST_LONG = "destLong";
	private static final String DEST_LAT = "destLat";
	private static final String ORIGIN_LONG = "originLong";
	private static final String ORIGIN_LAT = "originLat";

	private ProcessedDataDao processedDataDao;
	
	@Override
	public List<RatedCoordinate> getRatedPath(Coordinates start, 
			Coordinates end, TimeOfDay timeOfDay, Transport transport){
		RestTemplate restTemplate = new RestTemplate();
		String query = "http://maps.googleapis.com/maps/api/directions/json?origin={"+ORIGIN_LAT+"},{"+ORIGIN_LONG+"}&"
				+ "destination={"+DEST_LAT+"},{"+DEST_LONG+"}&sensor=false";
		
		Map<String, String> params = new HashMap<String, String>();
		params.put(ORIGIN_LAT, ""+start.getLatitude());
		params.put(ORIGIN_LONG, ""+start.getLongitude());
		params.put(DEST_LAT, ""+end.getLatitude());
		params.put(DEST_LONG, ""+end.getLongitude());
		
		ResponseEntity<String> restResponse = restTemplate.getForEntity(query, String.class, params);
		
		JsonElement jsonElement = new JsonParser().parse(restResponse.getBody());
		if(jsonElement.isJsonObject()){
			JsonObject json = (JsonObject) jsonElement;
			return extractPath(json);
		}
		
		return null;
	}

	private List<RatedCoordinate> extractPath(JsonObject json) {
		String callStatus = json.get("status").getAsString();
		if(json.entrySet().size()!=2){
			logger.error("invalid json object recieved");
			return null;
		}
		
		if("OK".equalsIgnoreCase(callStatus)){
			JsonElement routes = json.get("routes");
			JsonElement legs = routes.getAsJsonArray().get(0).getAsJsonObject().get("legs");
			JsonElement steps = legs.getAsJsonArray().get(0).getAsJsonObject().get("steps");
			
			JsonArray stepsArray = steps.getAsJsonArray();
			
			stepsArray = Commonutilities.limitJsonArraySize(stepsArray, 7);
			List<RatedCoordinate> ratedCoordinateList = new ArrayList<RatedCoordinate>();
			
			for (int i = 0; i < stepsArray.size(); i++) {
				JsonObject step = stepsArray.get(i).getAsJsonObject();
				JsonObject start_location  = step.get("start_location").getAsJsonObject();
				RatedCoordinate ratedCoordinate = getRatedCoordinateForLocationJson(start_location);				
				ratedCoordinateList.add(ratedCoordinate);
			}
			
			if(stepsArray.size() > 0){
				JsonObject step = stepsArray.get(stepsArray.size() - 1).getAsJsonObject();
				JsonObject end_location  = step.get("end_location").getAsJsonObject();
				RatedCoordinate lastCoordinate = getRatedCoordinateForLocationJson(end_location);
				ratedCoordinateList.add(lastCoordinate);
			}
			return ratedCoordinateList;
		}
		logger.error("google api call failed");
		return null;
	}
	
	private RatedCoordinate getRatedCoordinateForLocationJson(JsonObject locationJson){
		double lat = locationJson.get("lat").getAsDouble();
		double lng = locationJson.get("lng").getAsDouble();
		
		RatedCoordinate ratedCoordinate = new RatedCoordinate();
		ratedCoordinate.setLatitude(lat);
		ratedCoordinate.setLongitude(lng);
		ratedCoordinate.setRating(getProcessedDataDao().getRatingForCoordinate(ratedCoordinate.getCoordinates()));
		return ratedCoordinate;
	}

	public ProcessedDataDao getProcessedDataDao() {
		return processedDataDao;
	}

	public void setProcessedDataDao(ProcessedDataDao processedDataDao) {
		this.processedDataDao = processedDataDao;
	}
	
	
}
