package com.mcafee.scor.safety.action;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mcafee.scor.safety.dao.processedData.ProcessedDataDao;
import com.mcafee.scor.safety.dao.userRating.UserRatingDao;
import com.mcafee.scor.safety.model.Coordinates;
import com.mcafee.scor.safety.model.Rating;
import com.mcafee.scor.safety.model.TimeOfDay;
import com.mcafee.scor.safety.model.Transport;
import com.mcafee.scor.safety.model.UserRating;



@Controller
public class RatingPointsAction {

	@Autowired
	private ProcessedDataDao processedDataDao;
	@Autowired
	private UserRatingDao userRatingDao; 
	
	/**
	 * 
	 * sample url:
	 * http://localhost:8080/SafetyWebApp/getRatingMapAroundCoordinate.do?coordinateString=1,1&timeMillies=2345656&transport=3&radius=2
	 * 
	 * @param coordinateString
	 * @param timeMillies
	 * @param transport
	 * @param radius
	 * @return
	 */
	@RequestMapping(value="/getRatingMapAroundCoordinate.do", method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Map<Coordinates, Rating> getRatingOfRegionForCoordinate(@RequestParam("coordinateString") String coordinateString,
			@RequestParam("timeMillies") long timeMillies,
			@RequestParam("transport") int transport,
			@RequestParam("radius") int radius){
		TimeOfDay timeOfDay = TimeOfDay.getByTimeMillies(timeMillies);
		Transport transportEnum = Transport.getByIntVal(transport);
		return getProcessedDataDao().getRatingAroundCoordinate(Coordinates.getFromString(coordinateString), timeOfDay, transportEnum, radius);
	}
	
	/**
	 * sample url:
	 * http://localhost:8080/SafetyWebApp/saveRating.do?coordinateString=1,1&timeMillies=223543463&transport=2&rating=3
	 * 
	 * @param coordinateString
	 * @param timeMillies
	 * @param transport
	 * @param rating
	 * @return
	 */
	@RequestMapping(value="/saveRating.do",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody int saveRating(@RequestParam("coordinateString") String coordinateString,
			@RequestParam("timeMillies") long timeMillies,
			@RequestParam("transport") int transport,
			@RequestParam("rating") int rating){
		UserRating userRating = new UserRating();
		Coordinates coordinate = Coordinates.getFromString(coordinateString);
		
		userRating.setLatitude(coordinate.getLatitude());
		userRating.setLongitude(coordinate.getLongitude());
		userRating.setRating(Rating.getByIntegerValue(rating));
		userRating.setTimeOfDay(TimeOfDay.getByTimeMillies(timeMillies));
		Transport trasportObject = Transport.getByIntVal(transport);
		
		if(trasportObject == null){
			return ActionResponse.FAIL.getIntValue();
		}
		userRating.setTransport(trasportObject);
		
		getUserRatingDao().add(userRating);
		
		return ActionResponse.SUCCESS.getIntValue();
	}

	public ProcessedDataDao getProcessedDataDao() {
		return processedDataDao;
	}

	public void setProcessedDataDao(ProcessedDataDao processedDataDao) {
		this.processedDataDao = processedDataDao;
	}

	public UserRatingDao getUserRatingDao() {
		return userRatingDao;
	}

	public void setUserRatingDao(UserRatingDao userRatingDao) {
		this.userRatingDao = userRatingDao;
	}
	
}
