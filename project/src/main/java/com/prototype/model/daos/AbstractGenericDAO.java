package com.prototype.model.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

public class AbstractGenericDAO<T,K> implements GenericDAO<T,K> {
protected Class<T> entityClass;
private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("santoto_universidad");
@Override
public void save(T entity){
    EntityManager em = emf.createEntityManager();
    try{
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
    }catch(PersistenceException e){
        if(em.getTransaction().isActive())
        em.getTransaction().rollback();
    }finally{
        if(em.isOpen())
        em.close();
    }
}
@Override
public T findById(K id){
    EntityManager em = emf.createEntityManager();
    T entity = null;
    try{
     entity = em.find(entityClass, id);
    }catch(PersistenceException e){
        e.getMessage();
    }finally{
        if(em.isOpen())
        em.close();
    }
    return entity;
}
 @SuppressWarnings("unchecked")
@Override
public List<T> findAll(){
    EntityManager em =emf.createEntityManager();
    List<T> entities = null;
    try {
        entities = em.createQuery("FROM " + entityClass.getSimpleName()).getResultList();
    } catch (Exception e) {
        e.getMessage();
    }finally{
        if(em.isOpen())
        em.close();
    }
    return entities;
}
@Override
public void update(T entity){
    EntityManager em = emf.createEntityManager();
    try{
       em.getTransaction().begin();
       em.merge(entity);
       em.getTransaction().commit(); 
    } catch (PersistenceException e) {
        if(em.getTransaction().isActive()){
            em.getTransaction().rollback();
        }
    }finally{
        if(em.isOpen())
        em.close();
    }
}
@Override
public void delete(K id){
    EntityManager em = emf.createEntityManager();
    try {
        em.getTransaction().begin();
        T entity = em.find(entityClass, id);
        if(entity !=null)
        em.remove(entity);
        em.getTransaction().commit();
    } catch (PersistenceException e) {
        if(em.getTransaction().isActive()){
            em.getTransaction().rollback();
        }
    }finally{
        em.close();
    }
}

}
