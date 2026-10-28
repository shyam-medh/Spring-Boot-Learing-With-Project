package com.devopshub.deployment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

/**
 * Main Application: Spring Boot entry point for the DevOpsHub Deployment Service.
 */
@SpringBootApplication
public class DeploymentServiceApplication {

    static {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    /**
     * Caption: Main Bootstrap Runner
     * Launches the Spring Boot application context on port 8084.
     */
    public static void main(String[] args) {
        SpringApplication.run(DeploymentServiceApplication.class, args);
    }
}
