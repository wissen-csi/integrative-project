package com.prototype.model.daos;

import com.prototype.model.entities.BiomedicalEquipment;

/**
 * DAO class for handling persistence operations related to
 * {@link BiomedicalEquipment}.
 *
 * 
 * This class provides access to the generic CRUD logic defined in
 * {@code AbstractGenericDAO}, specifying {@code BiomedicalEquipment} as the
 * managed entity type and {@code Long} as its identifier.
 *
 * It serves as a data-access adapter within the persistence layer of the
 * application.
 */
public class BiomedicalEquipmentDAO extends AbstractGenericDAO<BiomedicalEquipment, Long> {

    /**
     * Creates a DAO instance configured to manage {@link BiomedicalEquipment}
     * entities, delegating all base CRUD behavior to the generic DAO superclass.
     */
    public BiomedicalEquipmentDAO() {
        super(BiomedicalEquipment.class);
    }
}
