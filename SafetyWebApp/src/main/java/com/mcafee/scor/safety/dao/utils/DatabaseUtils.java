package com.mcafee.scor.safety.dao.utils;

import org.hibernate.classic.Session;

public class DatabaseUtils {
	public static void closeSession(Session session){
		if(session!=null && session.isOpen()){
			session.close();
		}
	}

	public static String getInClause(int size) {
		StringBuilder inClause = new StringBuilder();
		inClause.append("(");
		if(size>0){
			for(int i=0;i<size;i++){
				inClause.append("?,");
			}
			inClause.setLength(inClause.length() - 1);	//remove extra ','
		}
		inClause.append(")");
		return inClause.toString();
	}
}
