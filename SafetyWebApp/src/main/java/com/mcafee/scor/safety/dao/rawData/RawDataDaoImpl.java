package com.mcafee.scor.safety.dao.rawData;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

import com.mcafee.scor.safety.dao.CommonBaseDaoImpl;
import com.mcafee.scor.safety.model.rawData.RawData;

public class RawDataDaoImpl extends CommonBaseDaoImpl<RawData> implements RawDataDao{
	@Override
	public List<RawData> readTopN(int lim) {
		Session session = null;
		Transaction trans = null;
		try{
			session = getSession();
			trans = session.beginTransaction();
			
			Query query = session.createQuery("From RawData");
			query.setMaxResults(lim);
			return query.list();
		}finally{
			commitTransaction(trans);
			closeSession(session);
		}
	}
}
