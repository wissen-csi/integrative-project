package com.prototype.model.daos;

import com.prototype.model.entities.TechEquipment;

/**
 * DAO class responsible for managing persistence operations related to
 * {@link TechEquipment} entities.
 *
 * <p>This class extends {@code AbstractGenericDAO}, specifying
 * {@code TechEquipment} as the managed entity type and {@code Long} as its
 * identifier. Through this configuration, it inherits all generic CRUD
 * functionality implemented using JPA.</p>
 *
 * <p>It acts as the persistence-layer adapter that provides structured
 * data-access capabilities for technical equipment records within the
 * application.</p>
 */
public class TechEquipmentDAO extends AbstractGenericDAO<TechEquipment, Long> {

    /**
     * Constructs a DAO instance configured to manage {@link TechEquipment}
     * entities. The entity class is passed to the generic DAO superclass
     * to enable type-safe CRUD operations.
     */
    public TechEquipmentDAO() {
        super(TechEquipment.class);
    }
}
