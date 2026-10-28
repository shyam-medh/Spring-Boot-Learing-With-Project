package com.devopshub.deployment.entity.enums;

/**
 * Enumeration: Represents the lifecycle states of a deployment execution run.
 */
public enum DeploymentStatus {
    PENDING,
    IN_PROGRESS,
    DEPLOYED,
    FAILED,
    CANCELLED,
    ROLLED_BACK
}
