package com.devopshub.project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "Request payload for registering a project in the DevOpsHub catalog")
public class ProjectCreateRequest {

    @Schema(description = "Project display name", example = "Payment Gateway Service")
    @NotBlank(message = "Project name is required")
    @Size(max = 100, message = "Project name must not exceed 100 characters")
    private String name;

    @Schema(description = "URL-friendly slug (lowercase alphanumeric and hyphens)", example = "payment-gateway-service")
    @NotBlank(message = "Slug is required")
    @Pattern(regexp = "^[a-z0-9-]+$", message = "Slug must contain only lowercase letters, numbers, and hyphens")
    private String slug;

    @Schema(description = "Project description", example = "Core payment processing microservice")
    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    @Schema(description = "Git repository URL", example = "https://github.com/myorg/payment-service")
    @NotBlank(message = "Git repository URL is required")
    private String gitRepoUrl;

    @Schema(description = "Default Git branch", example = "main")
    private String defaultBranch = "main";

    public ProjectCreateRequest() {}

    public ProjectCreateRequest(String name, String slug, String description, String gitRepoUrl, String defaultBranch) {
        this.name = name;
        this.slug = slug;
        this.description = description;
        this.gitRepoUrl = gitRepoUrl;
        this.defaultBranch = defaultBranch != null ? defaultBranch : "main";
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getGitRepoUrl() { return gitRepoUrl; }
    public void setGitRepoUrl(String gitRepoUrl) { this.gitRepoUrl = gitRepoUrl; }

    public String getDefaultBranch() { return defaultBranch; }
    public void setDefaultBranch(String defaultBranch) { this.defaultBranch = defaultBranch; }
}
