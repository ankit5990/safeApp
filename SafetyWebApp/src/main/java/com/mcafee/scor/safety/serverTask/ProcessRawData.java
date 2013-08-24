package com.mcafee.scor.safety.serverTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.mcafee.scor.safety.common.Commonutilities;
import com.mcafee.scor.safety.dao.processedData.ProcessedDataDao;
import com.mcafee.scor.safety.dao.rawData.RawDataDao;
import com.mcafee.scor.safety.model.processedData.ProcessedData;
import com.mcafee.scor.safety.model.rawData.RawData;
import com.mcafee.scor.safety.vo.ProcessedDataVO;

public class ProcessRawData extends CommonServerTask{
	private static final Logger logger = Logger.getLogger(ProcessRawData.class);

	private static final int lim = 10;
	
	private RawDataDao rawDataDao;
	private ProcessedDataDao processedDataDao; 
	
	public RawDataDao getRawDataDao() {
		return rawDataDao;
	}

	public void setRawDataDao(RawDataDao rawDataDao) {
		this.rawDataDao = rawDataDao;
	}

	public ProcessedDataDao getProcessedDataDao() {
		return processedDataDao;
	}

	public void setProcessedDataDao(ProcessedDataDao processedDataDao) {
		this.processedDataDao = processedDataDao;
	}

	public ProcessRawData(long delay) {
		super(delay);
	}

	@Override
	protected void runTask() {
		List<RawData> rawDataElements = getRawDataDao().readTopN(lim);
		
		if(Commonutilities.isNullOrEmpty(rawDataElements)){
			logger.debug("no data to process");
			return;
		}
		
		Map<ProcessedDataVO, Integer> processedDataVoToCountMap = new HashMap<ProcessedDataVO, Integer>();
		for(RawData rawData : rawDataElements){
			ProcessedDataVO processedDataVO = ProcessedDataVO.getProcessedDataVOForRawData(rawData);
			if(processedDataVoToCountMap.containsKey(processedDataVO)){
				Integer count = processedDataVoToCountMap.get(processedDataVO) + 1;
				processedDataVoToCountMap.put(processedDataVO, count);
			}else{
				processedDataVoToCountMap.put(processedDataVO, 1);
			}
		}
		
		List<ProcessedData> processedDataList = ProcessedDataVO.getProcessedDataListFromVo(processedDataVoToCountMap);
		processedDataDao.updateCrimeCount(processedDataList);
		
		getRawDataDao().deleteBatch(rawDataElements);
	}

}
