package com.prototype.model.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Generic abstract implementation of the {@link GenericDAO} interface.
 * This class provides common CRUD operations for any entity type and serves
 * as the base class for DAOs that require database interaction using JPA.
 *
 * @param <T> the entity type handled by the DAO
 * @param <K> the type of the entity's primary key
 * @version 1.0
 * author Jorge Forero
 */
public class AbstractGenericDAO<T, K> implements GenericDAO<T, K> {

    /**
     * Represents the entity class handled by the DAO.
     * This value is provided dynamically using reflection.
     */
    protected Class<T> entityClass;

    /**
     * Shared {@link EntityManagerFactory} instance used to create
     * entity managers for database operations. It is initialized
     * using the persistence unit defined in the configuration file.
     */
    protected static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");

    /**
     * Constructor that sets the entity class type for the DAO.
     *
     * @param entityClass the class object representing the entity type
     */
    protected AbstractGenericDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * Persists a new entity in the database.
     *
     * @param entity the entity instance to save
     * @return the saved entity
     */
    @Override
    public T save(T entity) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
        em.close();
        return entity;
    }

    /**
     * Finds an entity in the database by its primary key.
     *
     * @param id the identifier of the entity to find
     * @return the entity instance or {@code null} if not found
     */
    @Override
    public T findById(K id) {
        EntityManager em = emf.createEntityManager();
        T entity = em.find(entityClass, id);
        em.close();
        return entity;
    }

    /**
     * Retrieves all instances of the entity from the database.
     *
     * @return a list containing all persisted entities of type T
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<T> findAll() {
        EntityManager em = emf.createEntityManager();
        List<T> list = em.createQuery("FROM " + entityClass.getSimpleName()).getResultList();
        em.close();
        return list;
    }

    /**
     * Updates an existing entity in the database.
     *
     * @param entity the entity to update
     * @return the managed and updated entity instance
     */
    @Override
    public T update(T entity) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        T merged = em.merge(entity);
        em.getTransaction().commit();
        em.close();
        return merged;
    }

    /**
     * Deletes an entity from the database using its primary key.
     *
     * @param id the identifier of the entity to delete
     * @return the deleted entity, or {@code null} if not found
     */
    @Override
    public T delete(K id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        T entity = em.find(entityClass, id);
        if (entity != null) {
            em.remove(entity);
        }
        em.getTransaction().commit();
        em.close();
        return entity;
    }

}
