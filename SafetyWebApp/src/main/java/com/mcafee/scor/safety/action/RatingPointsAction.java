package com.mcafee.scor.safety.action;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mcafee.scor.safety.dao.processedData.ProcessedDataDao;
import com.mcafee.scor.safety.model.Coordinates;
import com.mcafee.scor.safety.model.Rating;



@Controller
public class RatingPointsAction {

	@Autowired
	private ProcessedDataDao processedDataDao;
	
	@RequestMapping(value="/getRatingMapAroundCoordinate.do", method=RequestMethod.GET)
	public @ResponseBody Map<Coordinates, Rating> getRatingOfRegionForCoordinate(@RequestParam("coordinateString") String coordinateString,
			@RequestParam("radius") int radius){
		return getProcessedDataDao().getRatingAroundCoordinate(Coordinates.getFromString(coordinateString), radius);
	}

	public ProcessedDataDao getProcessedDataDao() {
		return processedDataDao;
	}

	public void setProcessedDataDao(ProcessedDataDao processedDataDao) {
		this.processedDataDao = processedDataDao;
	}
	
}
