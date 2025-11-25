package com.prototype.model.daos;

import com.prototype.model.entities.Provider;

/**
 * DAO class responsible for handling persistence operations related to
 * {@link Provider} entities.
 *
 * <p>This class extends {@code AbstractGenericDAO}, specifying
 * {@code Provider} as the managed entity type and {@code Long} as its identifier.
 * Through this configuration, it inherits reusable and type-safe CRUD
 * functionality implemented using JPA.</p>
 *
 * <p>It serves as the infrastructure-layer adapter that provides structured
 * data-access behavior for provider records within the application.</p>
 */
public class ProviderDAO extends AbstractGenericDAO<Provider, Long> {

    /**
     * Constructs a DAO instance configured to manage {@link Provider}
     * entities, delegating the entity class to the generic DAO superclass to
     * enable standardized CRUD operations.
     */
    public ProviderDAO() {
        super(Provider.class);
    }
}

