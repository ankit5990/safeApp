package com.mcafee.scor.safety.dao;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

import com.mcafee.scor.safety.dao.utils.DatabaseUtils;


//TODO: refactor to not commit in the function.  
public abstract class CommonBaseDaoImpl<T> implements CommonBaseDao<T>{
	public SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public Session getSession() {
		return getSessionFactory().openSession();
	}
	
	public void closeSession(Session session){
		if(session!=null){
			session.flush();
		}
		DatabaseUtils.closeSession(session);
	}

	@Override
	public void add(T obj){
		Session session = null;
		Transaction trans = null;
		try{
			session = getSession();
			trans = session.beginTransaction();
			session.save(obj);
		}finally{
			commitTransaction(trans);
			closeSession(session);
		}
	}

	@Override
	public void update(T obj) {
		Session session = null;
		Transaction trans = null;
		try{
			session = getSession();
			trans = session.beginTransaction();
			
			session.update(obj);
			
		}finally{
			commitTransaction(trans);
			closeSession(session);
		}
	}
	
	protected void commitTransaction(Transaction trans) {
		if(trans != null && !trans.wasCommitted()){
			trans.commit();
		}
	}

	@Override
	public void addOrUpdate(T obj) {
		Session session = null;
		Transaction trans = null;
		try{
			session = getSession();
			trans = session.beginTransaction();
			
			session.merge(obj);
		}finally{		
			commitTransaction(trans);
			closeSession(session);
		}
	}

	@Override
	public T read(Class<T> clazz,int id){
		Session session = null;
		Transaction trans = null;
		try{
			session = getSession();
			trans = session.beginTransaction();
			
			return (T) session.get(clazz,id);
		}finally{
			commitTransaction(trans);
			closeSession(session);
		}
	}
	
}
