package com.devopshub.environment.entity;

import com.devopshub.environment.entity.enums.EnvironmentStatus;
import com.devopshub.environment.entity.enums.EnvironmentType;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.Objects;

/**
 * Domain Entity: Represents a deployment target environment associated with a project.
 * Stored in PostgreSQL database: devopshub_environments.
 */
@Entity
@Table(
    name = "environments",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_project_environment_name", columnNames = {"project_id", "name"})
    }
)
public class Environment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "project_id", nullable = false)
    private Long projectId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 50)
    private EnvironmentType type;

    @Column(name = "server_url", nullable = false, length = 255)
    private String serverUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private EnvironmentStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    /**
     * Caption: Default No-Args Constructor
     * Required by Hibernate for entity instantiation via reflection.
     */
    public Environment() {
    }

    /**
     * Caption: Parameterized Domain Constructor
     * Used by the service layer to create new environment instances before persistence.
     */
    public Environment(Long projectId, String name, EnvironmentType type, String serverUrl, EnvironmentStatus status) {
        this.projectId = projectId;
        this.name = name;
        this.type = type;
        this.serverUrl = serverUrl;
        this.status = status != null ? status : EnvironmentStatus.UNKNOWN;
    }

    /**
     * Caption: PrePersist Lifecycle Hook
     * Automatically sets createdAt, updatedAt to UTC now, and defaults status to UNKNOWN if null.
     */
    @PrePersist
    protected void onCreate() {
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
        if (this.status == null) {
            this.status = EnvironmentStatus.UNKNOWN;
        }
    }

    /**
     * Caption: PreUpdate Lifecycle Hook
     * Updates the updatedAt audit timestamp right before an UPDATE query is executed.
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EnvironmentType getType() {
        return type;
    }

    public void setType(EnvironmentType type) {
        this.type = type;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public EnvironmentStatus getStatus() {
        return status;
    }

    public void setStatus(EnvironmentStatus status) {
        this.status = status;
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
        Environment that = (Environment) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Environment{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", serverUrl='" + serverUrl + '\'' +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
