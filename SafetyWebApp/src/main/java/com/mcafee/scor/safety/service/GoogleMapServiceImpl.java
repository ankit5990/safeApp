package com.mcafee.scor.safety.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.mcafee.scor.safety.model.Coordinates;
import com.mcafee.scor.safety.model.TimeOfDay;
import com.mcafee.scor.safety.model.Transport;



public class GoogleMapServiceImpl {
	private static final String DEST_LONG = "destLong";
	private static final String DEST_LAT = "destLat";
	private static final String ORIGIN_LONG = "originLong";
	private static final String ORIGIN_LAT = "originLat";

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
		
		ResponseEntity<String> x = restTemplate.getForEntity(query, String.class, params);
		
		System.out.println(x.getBody());
		return null;
	}
}
