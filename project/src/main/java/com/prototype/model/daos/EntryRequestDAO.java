package com.prototype.model.daos;

import com.prototype.model.entities.EntryRequest;

/**
 * DAO class for managing persistence operations related to
 * {@link EntryRequest}.
 *
 * This class inherits all generic CRUD functionality from
 * {@code AbstractGenericDAO}, specifying {@code EntryRequest} as the
 * managed entity type and {@code Long} as its identifier.
 *
 * It acts as the persistence adapter responsible for entry request
 * data handling within the application's infrastructure layer.
 */
public class EntryRequestDAO extends AbstractGenericDAO<EntryRequest, Long> {

    /**
     * Constructs a DAO instance configured to work with {@link EntryRequest}
     * entities by delegating the entity class type to the generic DAO superclass.
     */
    public EntryRequestDAO() {
        super(EntryRequest.class);
    }

}
