package com.devopshub.environment.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration Layer: Configures Swagger/OpenAPI 3.0 metadata for Environment Service.
 */
@Configuration
public class OpenApiConfig {

    /**
     * Caption: Environment Service OpenAPI Specification Bean
     * Builds and exposes the OpenAPI 3.0 specification metadata, service title, version, and license.
     * Accessible via /v3/api-docs and /swagger-ui.html.
     *
     * @return Configured OpenAPI bean
     */
    @Bean
    public OpenAPI environmentServiceOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("DevOpsHub - Environment Service API")
                .description("Microservice for managing deployment environments, host targets, and live infrastructure health status.")
                .version("1.0.0")
                .contact(new Contact()
                    .name("DevOpsHub Platform Team")
                    .email("platform@devopshub.internal"))
                .license(new License()
                    .name("Apache 2.0")
                    .url("https://www.apache.org/licenses/LICENSE-2.0")));
    }
}
