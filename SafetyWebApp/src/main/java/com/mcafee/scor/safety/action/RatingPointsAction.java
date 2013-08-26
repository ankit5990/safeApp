package com.mcafee.scor.safety.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mcafee.scor.safety.model.rawData.RawData;



@Controller
public class RatingPointsAction {
	
	@RequestMapping(value="/getRatingMapAroundCoordinate.do", method=RequestMethod.GET)
	public @ResponseBody RawData getRatingOfRegionForCoordinate(@RequestParam("coordinateString") String coordinateString){
		RawData rawData = new RawData();
		rawData.setId(1);
		rawData.setCoordinatesString(coordinateString);
		return rawData;
	}
}
