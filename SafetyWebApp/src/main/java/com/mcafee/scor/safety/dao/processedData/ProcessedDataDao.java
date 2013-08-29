package com.mcafee.scor.safety.dao.processedData;

import java.util.List;
import java.util.Map;

import com.mcafee.scor.safety.dao.CommonBaseDao;
import com.mcafee.scor.safety.model.Coordinates;
import com.mcafee.scor.safety.model.Rating;
import com.mcafee.scor.safety.model.TimeOfDay;
import com.mcafee.scor.safety.model.Transport;
import com.mcafee.scor.safety.model.processedData.ProcessedData;

public interface ProcessedDataDao extends CommonBaseDao<ProcessedData>{

	void updateCrimeCount(List<ProcessedData> processedDataList);

	public Map<Coordinates, Rating> getRatingAroundCoordinate(Coordinates coordinate,
			TimeOfDay timeOfDay, Transport transport, int radius);

}
