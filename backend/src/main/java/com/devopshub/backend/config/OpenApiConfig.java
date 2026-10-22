package com.devopshub.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

/**
 * OpenApiConfig: The Menu Designer
 * 
 * Configures the global OpenAPI 3 metadata and enables the JWT "Authorize" button
 * in Swagger UI so developers can test secured endpoints directly in the browser.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI devOpsHubOpenAPI() {
        final String securitySchemeName = "BearerAuth";

        return new OpenAPI()
                .info(new Info()
                        .title("DevOpsHub Developer Platform API")
                        .description("Production-grade RESTful APIs for DevOpsHub managing projects, teams, tasks, and authentication.")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("DevOpsHub Engineering")
                                .email("engineering@devopshub.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                // Enable global security requirement for the "Authorize" lock button
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("Enter your JWT token (without 'Bearer ' prefix). Obtain one via POST /api/auth/login.")));
    }
}
