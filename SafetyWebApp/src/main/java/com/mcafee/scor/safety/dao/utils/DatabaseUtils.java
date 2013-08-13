package com.mcafee.scor.safety.dao.utils;

import org.hibernate.classic.Session;

public class DatabaseUtils {
	public static void closeSession(Session session){
		if(session!=null && session.isOpen()){
			session.close();
		}
	}
}
