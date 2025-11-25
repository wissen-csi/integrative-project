package com.prototype.model.enums;

/**
 * Represents the different user roles available within the system.
 * These roles define the user's permissions, responsibilities, 
 * and the type of operations they are allowed to perform.
 */
public enum Role {

     WATCHMAN, // Security personnel responsible for monitoring and controlling access.
    ADMIN, // System administrator with full access and configuration privileges.
    DOCTOR, // Medical doctor authorized to manage and review clinical equipment records.
    NURSE, // Nurse responsible for handling operational and clinical support tasks.
    SECRETARY, // Administrative staff responsible for clerical and coordination duties.
    BOSS, // Department or area supervisor with authorization to oversee operations.
    MAINTENANCE_MAN; // Personnel in charge of performing maintenance and technical service tasks.
}