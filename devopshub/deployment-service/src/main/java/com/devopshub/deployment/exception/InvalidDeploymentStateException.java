package com.devopshub.deployment.exception;

/**
 * Exception: Thrown when an illegal deployment status transition is requested (e.g., updating a completed run).
 */
public class InvalidDeploymentStateException extends RuntimeException {
    public InvalidDeploymentStateException(String message) {
        super(message);
    }
}
