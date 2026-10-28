package com.devopshub.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

/**
 * Main Application: Spring Boot bootstrap for the DevOpsHub Notification Service.
 */
@SpringBootApplication
public class NotificationServiceApplication {

    static {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    /**
     * Caption: Main Bootstrap Runner
     * Launches the Spring Boot application context on port 8085.
     */
    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }
}

