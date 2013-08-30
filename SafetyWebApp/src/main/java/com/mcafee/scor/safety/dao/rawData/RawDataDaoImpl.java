package com.mcafee.scor.safety.dao.rawData;

import java.util.List;

import com.mcafee.scor.safety.dao.CommonBaseDaoImpl;
import com.mcafee.scor.safety.model.rawData.RawData;

public class RawDataDaoImpl extends CommonBaseDaoImpl<RawData> implements RawDataDao{
	@Override
	public List<RawData> readTopN(int lim) {
		return readList("RawData", lim);
	}
}
