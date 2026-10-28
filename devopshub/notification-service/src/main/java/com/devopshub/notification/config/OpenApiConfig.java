package com.devopshub.notification.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration Layer: Exposes OpenAPI 3.0 specification metadata for Notification Service.
 */
@Configuration
public class OpenApiConfig {

    /**
     * Caption: Notification Service OpenAPI Specification Bean
     * Builds and exposes the OpenAPI 3.0 specification metadata for Swagger UI at /swagger-ui.html.
     *
     * @return Configured OpenAPI bean
     */
    @Bean
    public OpenAPI notificationServiceOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("DevOpsHub - Notification Service API")
                .description("Microservice REST API for multi-channel pipeline alerts, incident notifications, and event messaging.")
                .version("1.0.0")
                .contact(new Contact()
                    .name("DevOpsHub Alerting Team")
                    .email("alerts@devopshub.internal"))
                .license(new License()
                    .name("Apache 2.0")
                    .url("https://www.apache.org/licenses/LICENSE-2.0")));
    }
}

