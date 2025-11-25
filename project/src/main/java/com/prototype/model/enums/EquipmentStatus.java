package com.prototype.model.enums;

/**
 * Represents the set of possible states in the life cycle of a piece of technological
 * or biomedical equipment within the access control system.
 * 
 * Each state describes the current condition of the equipment, its availability,
 * and whether it requires technical or administrative intervention.
 */
public enum EquipmentStatus {

    NEW, // The equipment is new and has not been used. 
    IN_USE, // The equipment is currently in use.
    UNDER_MAINTENANCE, // The equipment is undergoing preventive or corrective maintenance.
    DAMAGED, // The equipment has damage that affects its operation.
    LOST, // The equipment is lost and unavailable
    DECOMMISSIONED, // The equipment has been permanently removed from inventory.
    IN_STORAGE, // The equipment is in storage and available for use or assignment.
    RESERVED, // The equipment is temporarily set aside for a user or process.
    PENDING_INSPECTION, // The equipment is awaiting inspection or verification.
    REPLACEMENT_NEEDED, // The equipment needs to be replaced due to failure or wear and tear.
    RECOVERED, //The equipment has been recovered after being lost or out of service.
    DONATED, // The equipment has been donated and no longer belongs to the institutional inventory.
    DISPOSED; // The equipment has been permanently discarded.
}