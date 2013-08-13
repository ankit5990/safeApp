package com.mcafee.scor.safety.dao;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

import com.mcafee.scor.safety.dao.utils.DatabaseUtils;

public abstract class CommonBaseDaoImpl<T> implements CommonBaseDao<T>{
	public SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public Session getSession() {
		/*Session session = getSessionFactory().getCurrentSession();
		if(session == null || !session.isOpen() ){
			return session;
		}*/
		return getSessionFactory().openSession();
	}
	
	public void closeSession(Session session){
		DatabaseUtils.closeSession(session);
	}

	@Override
	public void add(T obj){
		Session session = getSession();
		Transaction trans = session.beginTransaction();
		
		session.save(obj);
		
		trans.commit();
		closeSession(session);
	}

	@Override
	public void update(T obj) {
		Session session = getSession();
		Transaction trans = session.beginTransaction();
		
		session.update(obj);
		
		trans.commit();
		closeSession(session);
	}
	
	@Override
	public void addOrUpdate(T obj) {
		Session session = getSession();
		Transaction trans = session.beginTransaction();
		
		session.saveOrUpdate(obj);
		
		trans.commit();
		closeSession(session);
	}
	
}
