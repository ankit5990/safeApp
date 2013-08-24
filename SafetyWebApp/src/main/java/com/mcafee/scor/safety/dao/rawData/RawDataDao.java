package com.mcafee.scor.safety.dao.rawData;

import java.util.List;

import com.mcafee.scor.safety.dao.CommonBaseDao;
import com.mcafee.scor.safety.model.rawData.RawData;



public interface RawDataDao extends CommonBaseDao<RawData> {
	List<RawData> readTopN(int lim);
}
