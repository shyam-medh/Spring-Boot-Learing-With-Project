package com.devopshub.notification.exception;

/**
 * Exception: Thrown when a requested notification ID does not exist in the database.
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}

