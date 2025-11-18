package com.prototype.model.daos;

import com.prototype.model.entities.Provider;

/**
 * DAO class responsible for managing persistence operations for
 * {@link Provider} entities.
 *
 * This class extends {@code AbstractGenericDAO}, providing type information
 * so that generic CRUD operations can be executed using JPA for
 * {@code Provider}
 * objects. It acts as an infrastructure-layer adapter that handles data access
 * related to provider records.
 */
public class ProviderDAO extends AbstractGenericDAO<Provider, Long> {

    /**
     * Creates a DAO instance configured to work with {@link Provider} entities
     * by supplying the entity class to the generic DAO superclass.
     */
    public ProviderDAO() {
        super(Provider.class);
    }
}
