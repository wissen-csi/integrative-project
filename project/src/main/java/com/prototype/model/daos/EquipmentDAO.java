package com.prototype.model.daos;

import com.prototype.model.entities.Equipment;

/**
 * DAO class responsible for managing persistence operations for
 * {@link Equipment} entities.
 *
 * This class delegates all generic CRUD behavior to {@code AbstractGenericDAO},
 * specifying {@code Equipment} as the managed entity type and {@code Long} as
 * its
 * primary key type.
 *
 * It serves as a persistence-layer adapter that provides data-access
 * functionality
 * for equipment records within the application.
 */
public class EquipmentDAO extends AbstractGenericDAO<Equipment, Long> {

    /**
     * Creates a new DAO instance configured to work with {@link Equipment}
     * entities. The entity class type is passed to the generic DAO superclass
     * to enable type-safe CRUD operations.
     */
    public EquipmentDAO() {
        super(Equipment.class);
    }
}
