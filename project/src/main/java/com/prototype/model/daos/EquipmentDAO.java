package com.prototype.model.daos;

import com.prototype.model.entities.Equipment;

/**
 * DAO class responsible for handling persistence operations related to
 * {@link Equipment} entities.
 *
 * <p>This class extends {@code AbstractGenericDAO}, specifying
 * {@code Equipment} as the managed entity type and {@code Long} as the
 * identifier type. By doing so, it inherits all generic CRUD functionality,
 * allowing standardized data-access operations across the application.</p>
 *
 * <p>It acts as the persistence-layer adapter for equipment records,
 * providing structured access to the underlying database.</p>
 */
public class EquipmentDAO extends AbstractGenericDAO<Equipment, Long> {

    /**
     * Constructs a DAO instance configured to manage {@link Equipment}
     * entities. The entity class is passed to the superclass to enable
     * type-safe CRUD operations.
     */
    public EquipmentDAO() {
        super(Equipment.class);
    }
}
