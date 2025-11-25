package com.prototype.model.daos;

import com.prototype.model.entities.Person;

/**
 * DAO class responsible for managing persistence operations related to
 * {@link Person} entities.
 *
 * <p>This class extends {@code AbstractGenericDAO}, specifying
 * {@code Person} as the managed entity type and {@code Long} as the identifier,
 * thereby inheriting all generic CRUD functionality.</p>
 *
 * <p>It acts as the persistence-layer adapter that provides structured
 * data-access capabilities for person records within the application.</p>
 */
public class PersonDAO extends AbstractGenericDAO<Person, Long> {

    /**
     * Constructs a DAO instance configured to manage {@link Person} entities.
     * The entity type is delegated to the generic DAO superclass to enable
     * type-safe and reusable JPA-based CRUD operations.
     */
    public PersonDAO() {
        super(Person.class);
    }
}
