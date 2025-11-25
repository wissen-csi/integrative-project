package com.prototype.model.daos;

import javax.persistence.EntityManager;

import com.prototype.model.entities.EntryRequest;

/**
 * DAO class responsible for managing persistence operations related to
 * {@link EntryRequest} entities.
 * <p>
 * This class inherits generic CRUD behavior from {@link AbstractGenericDAO},
 * defining {@code EntryRequest} as the managed entity type and {@code Long}
 * as the primary key type.
 * </p>
 * <p>
 * It serves as a specialized persistence adapter within the application's
 * infrastructure layer for handling entry request records.
 * </p>
 */
public class EntryRequestDAO extends AbstractGenericDAO<EntryRequest, Long> {

    /**
     * Constructs a new DAO instance configured to operate with
     * {@link EntryRequest} entities by passing the entity class type to the
     * generic DAO superclass.
     */
    public EntryRequestDAO() {
        super(EntryRequest.class);
    }

    /**
     * Retrieves the most recent {@link EntryRequest} made by a specific person
     * for a specific piece of equipment.
     *
     * @param equipmentId the identifier of the equipment involved in the request
     * @param personId the identifier of the person who made the request
     * @return the most recent matching {@code EntryRequest}, or {@code null} if no
     *         result is found
     *
     * @throws javax.persistence.NoResultException if no matching entry request exists
     *         and no result is allowed by the underlying query execution
     */
    public EntryRequest lastRequest(Long equipmentId, Long personId) {
        EntityManager em = emf.createEntityManager();
        return em.createQuery(
                "SELECT s FROM EntryRequest s WHERE s.requester.id = :idPersona AND s.equipment.id = :idEquipment ORDER BY s.requestedAt DESC",
                entityClass)
                .setParameter("idPersona", personId)
                .setParameter("idEquipment", equipmentId)
                .setMaxResults(1)
                .getSingleResult();
    }
}
