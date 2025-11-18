package com.prototype.model.daos;

import com.prototype.model.entities.Person;

/**
 * DAO class responsible for handling persistence operations related to
 * {@link Person} entities.
 *
 * This class relies on the generic CRUD functionality provided by
 * {@code AbstractGenericDAO},
 * specifying {@code Person} as the managed entity type and {@code Long} as its
 * identifier.
 *
 * It acts as the persistence-layer adapter for person records within the
 * application.
 */
public class PersonDAO extends AbstractGenericDAO<Person, Long> {

    /**
     * Constructs a DAO instance configured to work with {@link Person} entities,
     * passing the entity class type to the generic DAO superclass to enable
     * type-safe JPA operations.
     */
    public PersonDAO() {
        super(Person.class);
    }

}
