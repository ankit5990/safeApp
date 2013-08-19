package com.mcafee.scor.safety.serverTask;

import com.mcafee.scor.safety.dao.rawData.RawDataDao;

public class ProcessRawData extends CommonServerTask{

	private RawDataDao rawDataDao;
	
	
	public RawDataDao getRawDataDao() {
		return rawDataDao;
	}

	public void setRawDataDao(RawDataDao rawDataDao) {
		this.rawDataDao = rawDataDao;
	}

	public ProcessRawData(long delay) {
		super(delay);
	}

	@Override
	protected void runTask() {
		// TODO Auto-generated method stub
		
	}

}
