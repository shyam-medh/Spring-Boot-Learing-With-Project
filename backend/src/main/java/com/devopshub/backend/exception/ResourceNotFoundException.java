package com.devopshub.backend.exception;

// We extend RuntimeException so Spring knows this is a crash!
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
