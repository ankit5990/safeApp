package com.mcafee.scor.safety.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.mcafee.scor.safety.model.Rating;
import com.mcafee.scor.safety.model.TimeOfDay;
import com.mcafee.scor.safety.model.processedData.ProcessedData;
import com.mcafee.scor.safety.model.rawData.RawData;

public class ProcessedDataVO {
	private ProcessedData processedData;
	
	private ProcessedDataVO(){
		
	}
	
	//returns a clone of ProcessedData to maintain immutability of the class
	public ProcessedData getProcessedData() {
		return new ProcessedData(processedData);
	}

	public ProcessedData getProcessedData(int crimeCount) {
		ProcessedData processedData = new ProcessedData(this.processedData);
		processedData.setNumberOfCrimes(crimeCount);
		return processedData;
	}
	
	public static ProcessedDataVO getProcessedDataVOForProcessedData(ProcessedData processedData){
		ProcessedDataVO processedDataVO = new ProcessedDataVO();
		processedDataVO.processedData = new ProcessedData(processedData);
		return processedDataVO;
	}
	
	public static ProcessedDataVO getProcessedDataVOForRawData(RawData rawData){
		ProcessedData processedData = new ProcessedData();
		processedData.setAutoId(0);
		processedData.setLatitude(rawData.getLatitude());
		processedData.setLongitude(rawData.getLongitude());
		processedData.setNumberOfCrimes(0);
		processedData.setRating(Rating.UNRATED);
		processedData.setStreetName(rawData.getStreetName());
		processedData.setTimeOfDay(TimeOfDay.getByTimeMillies(rawData.getTime()));
		processedData.setVictimTransport(rawData.getVictimTransport());
		
		return getProcessedDataVOForProcessedData(processedData);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		ProcessedData processedData = this.processedData;
		if(processedData == null){
			result = prime * result + 0;
		}
		else{
			result = prime * result + processedData.getAutoId();
			long temp;
			temp = Double.doubleToLongBits(processedData.getLatitude());
			result = prime * result + (int) (temp ^ (temp >>> 32));
			temp = Double.doubleToLongBits(processedData.getLongitude());
			result = prime * result + (int) (temp ^ (temp >>> 32));
//			result = prime * result + processedData.getNumberOfCrimes();
			result = prime * result + ((processedData.getRating() == null) ? 0 : processedData.getRating().hashCode());
			result = prime * result
					+ ((processedData.getStreetName() == null) ? 0 : processedData.getStreetName().hashCode());
			result = prime * result
					+ ((processedData.getTimeOfDay() == null) ? 0 : processedData.getTimeOfDay().hashCode());
			result = prime * result
					+ ((processedData.getVictimTransport() == null) ? 0 : processedData.getVictimTransport().hashCode());
			
		}
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProcessedDataVO other = (ProcessedDataVO) obj;
		if (processedData == null) {
			if (other.processedData != null)
				return false;
		} else if (processedData.equals(other.processedData))
			return true;
		
		if (processedData.getAutoId() != other.processedData.getAutoId())
			return false;
		if (Double.doubleToLongBits(processedData.getLatitude()) != Double
				.doubleToLongBits(other.getProcessedData().getLatitude()))
			return false;
		if (Double.doubleToLongBits(processedData.getLongitude()) != Double
				.doubleToLongBits(other.processedData.getLongitude()))
			return false;
//		if (processedData.getNumberOfCrimes() != other.processedData.getNumberOfCrimes())
//			return false;
		if (processedData.getRating() != other.processedData.getRating())
			return false;
		if (processedData.getStreetName() == null) {
			if (other.processedData.getStreetName() != null)
				return false;
		} else if (!processedData.getStreetName().equals(other.processedData.getStreetName()))
			return false;
		if (processedData.getTimeOfDay() != other.processedData.getTimeOfDay())
			return false;
		if (processedData.getVictimTransport() != other.processedData.getVictimTransport())
			return false;
	
		return true;
	}

	public static List<ProcessedData> getProcessedDataListFromVo(Map<ProcessedDataVO, Integer> processedDataVoToCrimeCountMap){
		List<ProcessedData> processedDataList = new ArrayList<ProcessedData>(processedDataVoToCrimeCountMap.size());
		
		for(Entry<ProcessedDataVO, Integer> entry : processedDataVoToCrimeCountMap.entrySet()){
			ProcessedData tempProcessedData = entry.getKey().getProcessedData();
			tempProcessedData.setNumberOfCrimes(entry.getValue());
			processedDataList.add(tempProcessedData);
		}
		
		return processedDataList;
	}
}
