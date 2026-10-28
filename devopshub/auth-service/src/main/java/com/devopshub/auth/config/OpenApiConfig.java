package com.devopshub.auth.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration Layer: Exposes OpenAPI 3.0 metadata for Auth Service endpoints.
 */
@Configuration
public class OpenApiConfig {

    /**
     * Caption: Auth Service OpenAPI Specification Bean
     * Builds and exposes the OpenAPI 3.0 specification metadata for Swagger UI.
     *
     * @return Configured OpenAPI bean
     */
    @Bean
    public OpenAPI authServiceOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("DevOpsHub - Auth Service API")
                .description("Microservice for user registration, identity authentication, and JWT token issuance.")
                .version("1.0.0")
                .contact(new Contact()
                    .name("DevOpsHub Platform Team")
                    .email("platform@devopshub.internal"))
                .license(new License()
                    .name("Apache 2.0")
                    .url("https://www.apache.org/licenses/LICENSE-2.0")));
    }
}
