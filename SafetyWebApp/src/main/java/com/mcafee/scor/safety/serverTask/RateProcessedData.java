package com.mcafee.scor.safety.serverTask;

import java.util.List;

import org.apache.log4j.Logger;

import com.mcafee.scor.safety.dao.processedData.ProcessedDataDao;
import com.mcafee.scor.safety.model.Rating;
import com.mcafee.scor.safety.model.processedData.ProcessedData;
import com.mcafee.scor.safety.ratingEngine.RatingEngine;

public class RateProcessedData extends CommonServerTask{
	private static final Logger logger = Logger.getLogger(RateProcessedData.class);

	private final int batchSize = 10;
	
	private int index = 1;
	
	private ProcessedDataDao processedDataDao;
	private RatingEngine ratingEngine;

	
	public RateProcessedData(long delay) {
		super(delay);
	}

	@Override
	protected void runTask() {
		int start = index;
		int end = index + batchSize - 1;
		List<ProcessedData> processedDataToRate = getProcessedDataDao().readRange(start , end);
		logger.debug("reading data from index range : "+start+" to "+end);
		if(processedDataToRate.isEmpty()){
			index = 1;
			logger.debug("reached end of records of processed data table. Resetting index");
			return;
		}
		for (ProcessedData processedData : processedDataToRate) {
			Rating rating = getRatingEngine().getRating(processedData);
			processedData.setRating(rating);
		}
		getProcessedDataDao().updateBatch(processedDataToRate);
		
		index = end + 1;
	}

	public ProcessedDataDao getProcessedDataDao() {
		return processedDataDao;
	}

	public void setProcessedDataDao(ProcessedDataDao processedDataDao) {
		this.processedDataDao = processedDataDao;
	}

	public RatingEngine getRatingEngine() {
		return ratingEngine;
	}

	public void setRatingEngine(RatingEngine ratingEngine) {
		this.ratingEngine = ratingEngine;
	}

}
