package com.broadsoft.xmcommon.androiddao;

import java.util.List;

public interface IDao<T> {

	public boolean add(T entity);
	public boolean update(T entity);
	public boolean delete(String guid); 
	public T findByPK(String guid); 
	public List<T> findAll();


}
