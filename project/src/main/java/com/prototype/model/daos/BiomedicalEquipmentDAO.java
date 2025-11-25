package com.prototype.model.daos;

import com.prototype.model.entities.BiomedicalEquipment;

/**
 * Data Access Object (DAO) for managing persistence operations related to
 * {@link BiomedicalEquipment} entities.
 * <p>
 * This class leverages the generic CRUD functionality provided by
 * {@link AbstractGenericDAO}, specifying {@code BiomedicalEquipment} as the
 * entity type and {@code Long} as the primary key type.
 * </p>
 * <p>
 * It functions as a specialized adapter within the application's persistence
 * layer, ensuring type safety and clear intention when interacting with
 * biomedical equipment records.
 * </p>
 */
public class BiomedicalEquipmentDAO extends AbstractGenericDAO<BiomedicalEquipment, Long> {

    /**
     * Constructs a new DAO instance configured specifically for handling
     * {@link BiomedicalEquipment} entities. The entity class is passed to the
     * generic superclass to enable its reusable CRUD operations.
     */
    public BiomedicalEquipmentDAO() {
        super(BiomedicalEquipment.class);
    }
}
