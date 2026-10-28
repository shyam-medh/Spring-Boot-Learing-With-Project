package com.devopshub.project.service;

import com.devopshub.project.dto.ProjectCreateRequest;
import com.devopshub.project.dto.ProjectResponse;
import com.devopshub.project.entity.Project;
import com.devopshub.project.entity.enums.ProjectStatus;
import com.devopshub.project.exception.DuplicateResourceException;
import com.devopshub.project.exception.ResourceNotFoundException;
import com.devopshub.project.repository.ProjectRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit Test: ProjectService business logic verification using Mockito.
 */
@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectService projectService;

    @Test
    @DisplayName("Should return all projects as response DTOs")
    void shouldReturnAllProjects_AsDTOs() {
        // GIVEN
        Project p1 = new Project("Payments Engine", "payments-engine", "Core payment processor", "https://github.com/org/payments", "main", ProjectStatus.ACTIVE);
        p1.setId(1L);
        Project p2 = new Project("Auth Gateway", "auth-gateway", "Security gateway", "https://github.com/org/auth", "main", ProjectStatus.ACTIVE);
        p2.setId(2L);

        when(projectRepository.findAll()).thenReturn(List.of(p1, p2));

        // WHEN
        List<ProjectResponse> result = projectService.getAllProjects();

        // THEN
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getSlug()).isEqualTo("payments-engine");
        assertThat(result.get(1).getSlug()).isEqualTo("auth-gateway");
        verify(projectRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should retrieve project by numeric ID when found")
    void shouldReturnProjectById_WhenFound() {
        // GIVEN
        Project project = new Project("Payments Engine", "payments-engine", "Core payment processor", "https://github.com/org/payments", "main", ProjectStatus.ACTIVE);
        project.setId(10L);
        when(projectRepository.findById(10L)).thenReturn(Optional.of(project));

        // WHEN
        ProjectResponse response = projectService.getProjectById(10L);

        // THEN
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(10L);
        assertThat(response.getName()).isEqualTo("Payments Engine");
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when project ID does not exist")
    void shouldThrowResourceNotFoundException_WhenProjectNotFound() {
        // GIVEN
        when(projectRepository.findById(999L)).thenReturn(Optional.empty());

        // WHEN & THEN
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () ->
            projectService.getProjectById(999L)
        );
        assertThat(ex.getMessage()).contains("Project not found with id: 999");
    }

    @Test
    @DisplayName("Should retrieve project by slug when found")
    void shouldReturnProjectBySlug_WhenFound() {
        // GIVEN
        Project project = new Project("Payments Engine", "payments-engine", "Desc", "https://github.com/org/payments", "main", ProjectStatus.ACTIVE);
        project.setId(1L);
        when(projectRepository.findBySlug("payments-engine")).thenReturn(Optional.of(project));

        // WHEN
        ProjectResponse response = projectService.getProjectBySlug("payments-engine");

        // THEN
        assertThat(response).isNotNull();
        assertThat(response.getSlug()).isEqualTo("payments-engine");
    }

    @Test
    @DisplayName("Should create new project successfully when name and slug are unique")
    void shouldCreateProject_WhenValid() {
        // GIVEN
        ProjectCreateRequest request = new ProjectCreateRequest(
            "Inventory Service",
            "inventory-service",
            "Warehouse tracking",
            "https://github.com/org/inventory",
            "main"
        );

        when(projectRepository.existsByName("Inventory Service")).thenReturn(false);
        when(projectRepository.existsBySlug("inventory-service")).thenReturn(false);

        Project savedProject = new Project("Inventory Service", "inventory-service", "Warehouse tracking", "https://github.com/org/inventory", "main", ProjectStatus.ACTIVE);
        savedProject.setId(5L);
        when(projectRepository.save(any(Project.class))).thenReturn(savedProject);

        // WHEN
        ProjectResponse response = projectService.createProject(request);

        // THEN
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(5L);
        assertThat(response.getName()).isEqualTo("Inventory Service");
        assertThat(response.getStatus()).isEqualTo(ProjectStatus.ACTIVE);
        verify(projectRepository, times(1)).save(any(Project.class));
    }

    @Test
    @DisplayName("Should throw DuplicateResourceException when project name already exists")
    void shouldThrowDuplicateResourceException_WhenNameExists() {
        // GIVEN
        ProjectCreateRequest request = new ProjectCreateRequest(
            "Existing Project", "slug-unique", "Desc", "https://github.com/org/repo", "main"
        );
        when(projectRepository.existsByName("Existing Project")).thenReturn(true);

        // WHEN & THEN
        assertThrows(DuplicateResourceException.class, () ->
            projectService.createProject(request)
        );
        verify(projectRepository, never()).save(any(Project.class));
    }

    @Test
    @DisplayName("Should throw DuplicateResourceException when project slug already exists")
    void shouldThrowDuplicateResourceException_WhenSlugExists() {
        // GIVEN
        ProjectCreateRequest request = new ProjectCreateRequest(
            "New Project", "duplicate-slug", "Desc", "https://github.com/org/repo", "main"
        );
        when(projectRepository.existsByName("New Project")).thenReturn(false);
        when(projectRepository.existsBySlug("duplicate-slug")).thenReturn(true);

        // WHEN & THEN
        assertThrows(DuplicateResourceException.class, () ->
            projectService.createProject(request)
        );
        verify(projectRepository, never()).save(any(Project.class));
    }
}
