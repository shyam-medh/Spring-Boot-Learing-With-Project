package com.devopshub.project.service;

import com.devopshub.project.dto.ProjectCreateRequest;
import com.devopshub.project.dto.ProjectResponse;
import com.devopshub.project.entity.Project;
import com.devopshub.project.exception.DuplicateResourceException;
import com.devopshub.project.exception.ResourceNotFoundException;
import com.devopshub.project.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Layer: Manages project catalog entries, Git repository linking, and project slug indexing.
 */
@Service
@Transactional(readOnly = true)
public class ProjectService {

    private final ProjectRepository projectRepository;

    /**
     * Caption: Constructor Injection
     * Injects ProjectRepository for database persistence operations.
     */
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    /**
     * Caption: Fetch All Projects
     * Retrieves all registered projects from PostgreSQL devopshub_projects and transforms them to DTOs.
     *
     * @return List of ProjectResponse records
     */
    public List<ProjectResponse> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Caption: Fetch Project by ID
     * Retrieves a project by its primary key identifier.
     *
     * @param id The project database ID
     * @return ProjectResponse The mapped project DTO
     * @throws ResourceNotFoundException If no project exists with the ID
     */
    public ProjectResponse getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));
        return toResponse(project);
    }

    /**
     * Caption: Fetch Project by Unique Slug
     * Retrieves a project by its URL-friendly slug identifier (e.g., "payment-gateway").
     *
     * @param slug The unique slug string
     * @return ProjectResponse The mapped project DTO
     * @throws ResourceNotFoundException If no project exists with the slug
     */
    public ProjectResponse getProjectBySlug(String slug) {
        Project project = projectRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with slug: " + slug));
        return toResponse(project);
    }

    /**
     * Caption: Create New Project
     * Validates name and slug uniqueness, configures default branch, persists the entity, and returns DTO.
     *
     * @param request Validated project creation request payload
     * @return ProjectResponse The newly created project DTO
     * @throws DuplicateResourceException If project name or slug is already taken
     */
    @Transactional
    public ProjectResponse createProject(ProjectCreateRequest request) {
        if (projectRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException("A project with name '" + request.getName() + "' already exists");
        }
        if (projectRepository.existsBySlug(request.getSlug())) {
            throw new DuplicateResourceException("A project with slug '" + request.getSlug() + "' already exists");
        }

        Project project = new Project();
        project.setName(request.getName());
        project.setSlug(request.getSlug());
        project.setDescription(request.getDescription());
        project.setGitRepoUrl(request.getGitRepoUrl());
        project.setDefaultBranch(
                request.getDefaultBranch() != null && !request.getDefaultBranch().isBlank()
                        ? request.getDefaultBranch()
                        : "main"
        );

        Project saved = projectRepository.save(project);
        return toResponse(saved);
    }

    /**
     * Caption: Helper to Map Entity to DTO
     * Transforms an internal Project JPA entity into a public ProjectResponse record.
     *
     * @param project The Project entity
     * @return ProjectResponse DTO record
     */
    private ProjectResponse toResponse(Project project) {
        return new ProjectResponse(
                project.getId(),
                project.getName(),
                project.getSlug(),
                project.getDescription(),
                project.getGitRepoUrl(),
                project.getDefaultBranch(),
                project.getStatus(),
                project.getCreatedAt(),
                project.getUpdatedAt()
        );
    }
}
