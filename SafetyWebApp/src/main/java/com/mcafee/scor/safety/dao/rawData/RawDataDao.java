package com.mcafee.scor.safety.dao.rawData;

import java.util.List;

import com.mcafee.scor.safety.model.rawData.RawData;



public interface RawDataDao {
	List<RawData> readTopN(int lim);
}
