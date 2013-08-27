package com.mcafee.scor.safety.action;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mcafee.scor.safety.model.Coordinates;
import com.mcafee.scor.safety.model.Rating;



@Controller
public class RatingPointsAction {
	
	@RequestMapping(value="/getRatingMapAroundCoordinate.do", method=RequestMethod.GET)
	public @ResponseBody Map<Coordinates, Rating> getRatingOfRegionForCoordinate(@RequestParam("coordinateString") String coordinateString,
			@RequestParam("radius") int radius){
		Map<Coordinates, Rating> coordinateToRatingMap = new HashMap<Coordinates, Rating>();
		coordinateToRatingMap.put(Coordinates.getFromString(coordinateString), Rating.GREEN);
		coordinateToRatingMap.put(Coordinates.getFromString(coordinateString+"1"), Rating.RED);
		return coordinateToRatingMap;
	}
}
