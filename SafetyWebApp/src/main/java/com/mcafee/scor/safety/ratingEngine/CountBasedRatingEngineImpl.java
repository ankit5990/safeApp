package com.mcafee.scor.safety.ratingEngine;

import com.mcafee.scor.safety.model.Rating;
import com.mcafee.scor.safety.model.processedData.ProcessedData;

public class CountBasedRatingEngineImpl implements RatingEngine	{

	@Override
	public Rating getRating(ProcessedData processedData) {
		if(processedData.getNumberOfCrimes() < 30){
			return Rating.GREEN;
		}
		if(processedData.getNumberOfCrimes() < 50){
			return Rating.YELLOW;
		}
		return Rating.RED;
	}

}
