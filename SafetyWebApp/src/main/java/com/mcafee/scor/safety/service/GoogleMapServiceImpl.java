package com.mcafee.scor.safety.service;

import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.mcafee.scor.safety.model.Coordinates;
import com.mcafee.scor.safety.model.TimeOfDay;
import com.mcafee.scor.safety.model.Transport;



public class GoogleMapServiceImpl {
	public List<RatedCoordinate> getRatedPath(Coordinates start, 
			Coordinates end, TimeOfDay timeOfDay, Transport transport){
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		HttpEntity<String> request = new HttpEntity<String>("body",httpHeaders);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> x = restTemplate.getForEntity("http://maps.googleapis.com/maps/api/directions/json?origin=28.6100,77.2300&destination=28.6100,78.2300&sensor=false", String.class);
		System.out.println(x);
		return null;
	}
}
