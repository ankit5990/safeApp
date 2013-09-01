package com.mcafee.scor.safety.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mcafee.scor.safety.model.Coordinates;
import com.mcafee.scor.safety.model.TimeOfDay;
import com.mcafee.scor.safety.model.Transport;
import com.mcafee.scor.safety.service.GoogleMapService;
import com.mcafee.scor.safety.vo.RatedPath;

@Controller
public class PathAction {

	@Autowired
	private GoogleMapService googleMapService;

	
	/**
	 * test url:
	 * http://localhost:8080/SafetyWebApp/getRatedPath.do?start=28.6100,77.2300&end=27.6100,78.2300&timeMillies=36544768&transport=2
	 * @param start
	 * @param end
	 * @param timeMillies
	 * @param transport
	 * @return
	 */
	@RequestMapping(value="/getRatedPath.do",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody RatedPath getRatedPathAction(@RequestParam("start")String start,
			@RequestParam("end") String end,
			@RequestParam("timeMillies") long timeMillies,
			@RequestParam("transport") int transport){
		Coordinates startCoordinate = Coordinates.getFromString(start);
		Coordinates endCoordinate = Coordinates.getFromString(end);
		TimeOfDay timeOfDay = TimeOfDay.getByTimeMillies(timeMillies);
		Transport transportObj = Transport.getByIntVal(transport);
		return getGoogleMapService().getRatedPath(startCoordinate , endCoordinate, timeOfDay, transportObj);
	}
	
	public GoogleMapService getGoogleMapService() {
		return googleMapService;
	}

	public void setGoogleMapService(GoogleMapService googleMapService) {
		this.googleMapService = googleMapService;
	}
}
