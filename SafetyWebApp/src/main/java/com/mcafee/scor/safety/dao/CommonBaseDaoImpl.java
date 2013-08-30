package com.mcafee.scor.safety.dao;

import java.util.List;

import org.hibernate.Query;
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
	
	public List<T> readList(String modelName,int lim){
		Session session = null;
		Transaction trans = null;
		try{
			session = getSession();
			trans = session.beginTransaction();
			
			Query query = session.createQuery("From "+modelName);
			query.setMaxResults(lim);
			
			return query.list();
		}finally{
			commitTransaction(trans);
			closeSession(session);
		}
	}
	
	@Override
	public void updateBatch(List<T> objects){
		Session session = null;
		Transaction trans = null;
		try{
			session = getSession();
			trans = session.beginTransaction();
			for(T t : objects){
				session.update(t);
			}
		}finally{
			commitTransaction(trans);
			closeSession(session);
		}
	}
	
	@Override
	public void deleteBatch(List<T> objects){
		Session session = null;
		Transaction trans = null;
		try{
			session = getSession();
			trans = session.beginTransaction();
			for(T t : objects){
				session.delete(t);
			}
		}finally{
			commitTransaction(trans);
			closeSession(session);
		}
	}
}
