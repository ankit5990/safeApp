package com.mcafee.scor.safety.dao;


public interface CommonBaseDao<T> {
	public void add(T obj);
	public void update(T obj);
	public void addOrUpdate(T obj);
}
