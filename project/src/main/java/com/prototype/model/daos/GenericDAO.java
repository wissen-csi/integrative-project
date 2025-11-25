package com.prototype.model.daos;

import java.util.List;

/**
 * Generic DAO interface that defines the basic CRUD operations to be implemented
 * by concrete data-access classes.
 *
 * <p>This interface abstracts the fundamental persistence operations for any
 * entity type, enabling reusable and type-safe data handling across the
 * application.</p>
 *
 * @param <T> the entity type managed by the DAO
 * @param <K> the type of the entity identifier (e.g., Long, String, UUID)
 */
public interface GenericDAO<T, K> {

    /**
     * Persists a new entity in the database.
     *
     * @param entity the entity to be saved
     * @return the persisted entity, possibly with updated state (e.g., generated ID)
     */
    T save(T entity);

    /**
     * Retrieves an entity by its identifier.
     *
     * @param key the identifier of the entity
     * @return the entity if found, otherwise {@code null}
     */
    T findById(K key);

    /**
     * Retrieves all records associated with the entity type from the database.
     *
     * @return a {@link List} containing all stored entities
     */
    List<T> findAll();

    /**
     * Updates the state of an existing entity in the database.
     *
     * @param entity the entity with updated data
     * @return the merged entity after persistence
     */
    T update(T entity);

    /**
     * Deletes an entity identified by the given key.
     *
     * @param id the identifier of the entity to remove
     * @return the removed entity, or {@code null} if it did not exist
     */
    T delete(K id);
}
