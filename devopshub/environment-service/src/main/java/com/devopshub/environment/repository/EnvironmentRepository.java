package com.devopshub.environment.repository;

import com.devopshub.environment.entity.Environment;
import com.devopshub.environment.entity.enums.EnvironmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository Layer: Spring Data JPA repository for the environments table in PostgreSQL.
 */
@Repository
public interface EnvironmentRepository extends JpaRepository<Environment, Long> {

    /**
     * Caption: Query Environments by Project ID
     * Finds all deployment environment entities linked to a specific project.
     *
     * @param projectId The foreign project identifier
     * @return List of matching Environment entities
     */
    List<Environment> findByProjectId(Long projectId);

    /**
     * Caption: Query Single Environment by Project ID and Name
     * Retrieves an environment matching the unique composite key (project_id, name).
     *
     * @param projectId The foreign project identifier
     * @param name The environment name (e.g., "production")
     * @return Optional containing the Environment if found, otherwise empty
     */
    Optional<Environment> findByProjectIdAndName(Long projectId, String name);

    /**
     * Caption: Check Existence by Project ID and Name
     * Fast boolean query used to prevent duplicate environment creation within the same project.
     *
     * @param projectId The foreign project identifier
     * @param name The environment name to test
     * @return true if an environment already exists with this name for this project, false otherwise
     */
    boolean existsByProjectIdAndName(Long projectId, String name);

    /**
     * Caption: Query Environments by Health Status
     * Filters all registered environments across the system by their operational status (e.g., DOWN).
     *
     * @param status The status enum value
     * @return List of matching environments
     */
    List<Environment> findByStatus(EnvironmentStatus status);
}
