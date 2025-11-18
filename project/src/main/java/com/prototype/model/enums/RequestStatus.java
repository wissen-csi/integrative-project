package com.prototype.model.enums;
/**
 * Represents the processing status of an equipment request.
 * 
 * This enumeration is used to describe the result or current state 
 * of operations related to request handling within the system.
 */
public enum RequestStatus {
    PENDING, // The request has been created and is awaiting processing. 
    SUCCESS, // The request was processed successfully.
    ERROR, // An unexpected issue occurred during processing.
    FAILED // The request could not be completed due to a known failure.
}