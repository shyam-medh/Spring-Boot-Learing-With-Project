package com.devopshub.deployment.repository;

import com.devopshub.deployment.entity.Deployment;
import com.devopshub.deployment.entity.enums.DeploymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository Layer: Spring Data JPA repository for deployments table in PostgreSQL devopshub_deployments.
 */
@Repository
public interface DeploymentRepository extends JpaRepository<Deployment, Long> {

    /**
     * Caption: Query Deployments by Project ID
     * Retrieves all deployment pipeline runs executed for a given project ID.
     *
     * @param projectId The project ID from project-service
     * @return List of matching Deployment entities
     */
    List<Deployment> findByProjectId(Long projectId);

    /**
     * Caption: Query Deployments by Environment ID
     * Retrieves deployment history targeting a specific environment ID.
     *
     * @param environmentId The environment ID from environment-service
     * @return List of matching Deployment entities
     */
    List<Deployment> findByEnvironmentId(Long environmentId);

    /**
     * Caption: Query Deployments by Project and Environment
     * Retrieves deployment history scoped to both project and target environment.
     *
     * @param projectId The project ID
     * @param environmentId The target environment ID
     * @return List of matching Deployment entities
     */
    List<Deployment> findByProjectIdAndEnvironmentId(Long projectId, Long environmentId);

    /**
     * Caption: Query Deployments by Status
     * Filters deployment records by operational lifecycle status (e.g. IN_PROGRESS, PENDING).
     *
     * @param status The target DeploymentStatus
     * @return List of matching Deployment entities
     */
    List<Deployment> findByStatus(DeploymentStatus status);

    /**
     * Caption: Query Recent Deployments for Project
     * Retrieves all deployments for a project ordered with newest executions first.
     *
     * @param projectId The project ID
     * @return Ordered list of Deployment entities
     */
    List<Deployment> findByProjectIdOrderByCreatedAtDesc(Long projectId);
}
