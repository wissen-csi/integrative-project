package com.prototype.model.daos;

import java.util.List;

/**
 * interface that defines CRUD methods for implementation in other classes
 * 
 * @param K Key generic
 * @param T Type generic
 * 
 * @author Jorge Forero
 * @version 1.0
 */
public interface GenericDAO<T, K> {

    /**
     * Persistence of object
     * 
     * @param entity
     */
    public void save(T entity);

    /**
     * Basic query of id/key
     * 
     * @param key
     * @return Generic type object
     */
    public T findById(K key);

    /**
     * Query that retrieves the entire table in the DB
     * 
     * @return {@link List} of items
     */
    public List<T> findAll();

    /**
     * Update of the object in DB
     * 
     * @param entity
     */
    public void update(T entity);

    /**
     * Delete of the object in DB
     * 
     * @param id
     */
    public void delete(K id);
}
