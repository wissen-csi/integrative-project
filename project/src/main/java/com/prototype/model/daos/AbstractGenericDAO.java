package com.prototype.model.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

/**
 * This class implements the interface {@link GenericDAO}, which is responsible
 * for applying the logic of the methods inherited by the interface.
 * All its implementations are designed for generic types.
 * 
 * @param T Type generic
 * @param K Key generic
 * @author Jorge Forero
 * @version 1.0
 */
class AbstractGenericDAO<T, K> implements GenericDAO<T, K> {
    /**
     * Attribute that represents the object of use; this uses refraction to define
     * it as dynamic
     */
    protected Class<T> entityClass;

    /**
     * constant used in conection with archive of persistence
     */
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");

    /**
     * Class Constructor
     * 
     * @param entityClass
     */
    protected AbstractGenericDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * implementation of the method of {@link GenericDAO}
     * 
     * @param enity
     */
    @Override
    public void save(T entity) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            if (em.getTransaction().isActive())
                em.getTransaction().rollback();
        } finally {
            if (em.isOpen())
                em.close();
        }
    }

    /**
     * implementation of the method of {@link GenericDAO}
     * 
     * @param id
     * @return generic object
     */
    @Override
    public T findById(K id) {
        EntityManager em = emf.createEntityManager();
        T entity = null;
        try {
            entity = em.find(entityClass, id);
        } catch (PersistenceException e) {
            e.getMessage();
        } finally {
            if (em.isOpen())
                em.close();
        }
        return entity;
    }

    /**
     * implementation of the method of {@link GenericDAO}
     * 
     * @return {@link List}
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<T> findAll() {
        EntityManager em = emf.createEntityManager();
        List<T> entities = null;
        try {
            entities = em.createQuery("FROM " + entityClass.getSimpleName()).getResultList();
        } catch (Exception e) {
            e.getMessage();
        } finally {
            if (em.isOpen())
                em.close();
        }
        return entities;
    }

    /**
     * implementation of the method of {@link GenericDAO}
     * 
     * @param entity
     */
    @Override
    public void update(T entity) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(entity);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            if (em.isOpen())
                em.close();
        }
    }

    /**
     * implementation of the method of {@link GenericDAO}
     * 
     * @param id
     */
    @Override
    public void delete(K id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            T entity = em.find(entityClass, id);
            if (entity != null)
                em.remove(entity);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            em.close();
        }
    }

}
