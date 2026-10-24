package com.devopshub.project.dto;

import com.devopshub.project.entity.enums.ProjectStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Response payload representing a registered DevOpsHub project")
public class ProjectResponse {

    @Schema(description = "Unique project ID", example = "1")
    private Long id;

    @Schema(description = "Project name", example = "Payment Gateway Service")
    private String name;

    @Schema(description = "Unique slug", example = "payment-gateway-service")
    private String slug;

    @Schema(description = "Project description")
    private String description;

    @Schema(description = "Git repository URL", example = "https://github.com/myorg/payment-service")
    private String gitRepoUrl;

    @Schema(description = "Default branch", example = "main")
    private String defaultBranch;

    @Schema(description = "Operational status", example = "ACTIVE")
    private ProjectStatus status;

    @Schema(description = "Creation timestamp")
    private LocalDateTime createdAt;

    @Schema(description = "Last updated timestamp")
    private LocalDateTime updatedAt;

    public ProjectResponse() {}

    public ProjectResponse(Long id, String name, String slug, String description,
                           String gitRepoUrl, String defaultBranch,
                           ProjectStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.description = description;
        this.gitRepoUrl = gitRepoUrl;
        this.defaultBranch = defaultBranch;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getSlug() { return slug; }
    public String getDescription() { return description; }
    public String getGitRepoUrl() { return gitRepoUrl; }
    public String getDefaultBranch() { return defaultBranch; }
    public ProjectStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
