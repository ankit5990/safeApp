package com.mcafee.scor.safety.ratingEngine;

import com.mcafee.scor.safety.model.Rating;
import com.mcafee.scor.safety.model.processedData.ProcessedData;

public interface RatingEngine {
	public Rating getRating(ProcessedData processedData);
}
