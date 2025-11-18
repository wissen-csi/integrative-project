package com.prototype.model.enums;
/**
 * Represents the different categories of equipment handled by the system.
 * 
 * This classification enables consistent organization and the application of 
 * specific business rules depending on the operational role of each equipment type.
 */
public enum EquipmentType {
    COMPUTING, // Computer equipment, networks, peripherals
    MEDICAL, // Clinical, hospital, or diagnostic equipment
    LABORATORY, // Physical, chemical, or biological analysis equipment
    AUDIOVISUAL, // Projectors, cameras, microphones, etc.
    OFFICE, // Administrative equipment, printers, scanners
    INFRASTRUCTURE, // Support equipment: UPS, servers
    OTHER; // Other unclassified types
}