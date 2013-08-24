package com.mcafee.scor.safety.dao.processedData;

import java.util.List;

import com.mcafee.scor.safety.dao.CommonBaseDao;
import com.mcafee.scor.safety.model.processedData.ProcessedData;

public interface ProcessedDataDao extends CommonBaseDao<ProcessedData>{

	void updateCrimeCount(List<ProcessedData> processedDataList);

}
