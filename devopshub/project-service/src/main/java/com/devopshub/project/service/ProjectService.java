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

@Service
@Transactional(readOnly = true)
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<ProjectResponse> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public ProjectResponse getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));
        return toResponse(project);
    }

    public ProjectResponse getProjectBySlug(String slug) {
        Project project = projectRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with slug: " + slug));
        return toResponse(project);
    }

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
