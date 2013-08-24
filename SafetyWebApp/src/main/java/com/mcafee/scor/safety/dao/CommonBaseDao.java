package com.mcafee.scor.safety.dao;

import java.util.List;



public interface CommonBaseDao<T> {
	public void add(T obj);
	public void update(T obj);
	public void addOrUpdate(T obj);
	public T read(Class<T> clazz, int id);
	public void updateBatch(List<T> objects);
	public void deleteBatch(List<T> objects);
}
