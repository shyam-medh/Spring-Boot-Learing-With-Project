package com.devopshub.deployment.entity;

import com.devopshub.deployment.entity.enums.DeploymentStatus;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.Objects;

/**
 * Domain Entity: Represents a deployment execution run of an artifact version
 * onto a specific target environment for a project.
 * Stored in PostgreSQL database: devopshub_deployments.
 */
@Entity
@Table(
    name = "deployments",
    indexes = {
        @Index(name = "idx_deployment_project_id", columnList = "project_id"),
        @Index(name = "idx_deployment_environment_id", columnList = "environment_id"),
        @Index(name = "idx_deployment_status", columnList = "status")
    }
)
public class Deployment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "project_id", nullable = false)
    private Long projectId;

    @Column(name = "environment_id", nullable = false)
    private Long environmentId;

    @Column(name = "artifact_version", nullable = false, length = 100)
    private String artifactVersion;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private DeploymentStatus status;

    @Column(name = "triggered_by", nullable = false, length = 100)
    private String triggeredBy;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "started_at")
    private Instant startedAt;

    @Column(name = "completed_at")
    private Instant completedAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    /**
     * Caption: Default No-Args Constructor
     * Required by Hibernate for entity instantiation via reflection.
     */
    public Deployment() {
    }

    /**
     * Caption: Parameterized Domain Constructor
     * Constructs a new pending deployment run ready to be persisted.
     */
    public Deployment(Long projectId, Long environmentId, String artifactVersion, String triggeredBy, String description) {
        this.projectId = projectId;
        this.environmentId = environmentId;
        this.artifactVersion = artifactVersion;
        this.triggeredBy = triggeredBy;
        this.description = description;
        this.status = DeploymentStatus.PENDING;
    }

    /**
     * Caption: PrePersist Lifecycle Hook
     * Sets creation and update timestamps immediately before SQL INSERT.
     */
    @PrePersist
    protected void onCreate() {
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
        if (this.status == null) {
            this.status = DeploymentStatus.PENDING;
        }
    }

    /**
     * Caption: PreUpdate Lifecycle Hook
     * Updates audit timestamp before an existing row is updated.
     */
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getEnvironmentId() {
        return environmentId;
    }

    public void setEnvironmentId(Long environmentId) {
        this.environmentId = environmentId;
    }

    public String getArtifactVersion() {
        return artifactVersion;
    }

    public void setArtifactVersion(String artifactVersion) {
        this.artifactVersion = artifactVersion;
    }

    public DeploymentStatus getStatus() {
        return status;
    }

    public void setStatus(DeploymentStatus status) {
        this.status = status;
    }

    public String getTriggeredBy() {
        return triggeredBy;
    }

    public void setTriggeredBy(String triggeredBy) {
        this.triggeredBy = triggeredBy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Instant startedAt) {
        this.startedAt = startedAt;
    }

    public Instant getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Instant completedAt) {
        this.completedAt = completedAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Deployment that = (Deployment) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Deployment{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", environmentId=" + environmentId +
                ", artifactVersion='" + artifactVersion + '\'' +
                ", status=" + status +
                ", triggeredBy='" + triggeredBy + '\'' +
                ", startedAt=" + startedAt +
                ", completedAt=" + completedAt +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
