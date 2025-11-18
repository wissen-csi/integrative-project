package com.prototype.model.daos;

/**
 * DAO class that delegates persistence operations to
 * {@link AbstractGenericDAO},
 * using {@code TechEquipmentDAO} as the managed type and {@code Long} as the
 * identifier type.
 *
 * This class provides the required type information to the generic DAO
 * superclass for executing basic CRUD operations within the persistence layer.
 */
public class TechEquipmentDAO extends AbstractGenericDAO<TechEquipmentDAO, Long> {

    /**
     * Constructs a DAO instance configured to handle {@code TechEquipmentDAO}
     * entities by passing the class reference to the generic DAO superclass.
     */
    public TechEquipmentDAO() {
        super(TechEquipmentDAO.class);
    }
}
