package com.devopshub.deployment.exception;

/**
 * Exception: Thrown when a requested deployment ID does not exist in the database.
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
