package com.mcafee.scor.safety.serverTask;

import com.mcafee.scor.safety.dao.processedData.ProcessedDataDao;

public class RateProcessedData extends CommonServerTask{

	private final int batchSize = 10;
	
	private long index = 1;
	
	private ProcessedDataDao processedDataDao;
	
	public RateProcessedData(long delay) {
		super(delay);
	}

	@Override
	protected void runTask() {
		
	}

	public ProcessedDataDao getProcessedDataDao() {
		return processedDataDao;
	}

	public void setProcessedDataDao(ProcessedDataDao processedDataDao) {
		this.processedDataDao = processedDataDao;
	}
	
}
