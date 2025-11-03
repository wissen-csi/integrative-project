package com.prototype.model.daos;

import java.util.List;

public interface GenericDAO<T,K> {
// crud= create read update, delete
public void save(T entity);
public T findById(K key);
public List<T> findAll();
public void update(T entity);
public void delete(K id);
}
