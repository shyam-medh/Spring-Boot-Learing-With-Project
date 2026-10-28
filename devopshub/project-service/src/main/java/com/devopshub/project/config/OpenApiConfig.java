package com.devopshub.project.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration Layer: Exposes OpenAPI 3.0 specification for Project Service.
 */
@Configuration
public class OpenApiConfig {

    /**
     * Caption: Project Service OpenAPI Specification Bean
     * Builds and exposes the OpenAPI 3.0 specification metadata for Swagger UI at /swagger-ui.html.
     *
     * @return Configured OpenAPI bean
     */
    @Bean
    public OpenAPI projectServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("DevOpsHub - Project Service API")
                        .description("Microservice REST API for managing project service catalogs, Git repositories, and metadata.")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("DevOpsHub Engineering")
                                .email("engineering@devopshub.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")));
    }
}
