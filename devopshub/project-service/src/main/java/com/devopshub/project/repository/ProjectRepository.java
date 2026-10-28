package com.devopshub.project.repository;

import com.devopshub.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository Layer: Spring Data JPA repository for the projects table in PostgreSQL devopshub_projects.
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    /**
     * Caption: Query Project by Unique URL Slug
     * Finds a project entity by its human-readable slug string.
     *
     * @param slug The unique project slug
     * @return Optional containing the Project if found, otherwise empty
     */
    Optional<Project> findBySlug(String slug);

    /**
     * Caption: Check Existence by Slug
     * Used to prevent collisions when assigning or generating project slugs.
     *
     * @param slug The slug to verify
     * @return true if slug exists, false otherwise
     */
    boolean existsBySlug(String slug);

    /**
     * Caption: Check Existence by Name
     * Used to enforce unique display names across the platform.
     *
     * @param name The project name to verify
     * @return true if name exists, false otherwise
     */
    boolean existsByName(String name);
}
