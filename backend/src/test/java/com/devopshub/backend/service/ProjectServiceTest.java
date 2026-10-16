package com.devopshub.backend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.devopshub.backend.dto.ProjectDTO;
import com.devopshub.backend.entity.Project;
import com.devopshub.backend.exception.ResourceNotFoundException;
import com.devopshub.backend.repository.ProjectRepository;

@ExtendWith(MockitoExtension.class) // Enable Mockito in JUnit 5
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository; // Stunt Double (Mock)

    @InjectMocks
    private ProjectService projectService; // Real Service with Mock injected

    // TEST 1: Get All Projects
    @Test
    void shouldReturnAllProjects_AsDTOs() {
        // GIVEN: Set up what the fake repository returns
        Project p1 = new Project("DevOps Pipeline", "ACTIVE");
        p1.setId(1L);
        Project p2 = new Project("Cloud Migration", "COMPLETED");
        p2.setId(2L);

        when(projectRepository.findAll()).thenReturn(List.of(p1, p2));

        // WHEN: Calling the service method
        List<ProjectDTO> result = projectService.getAllProjectS();

        // THEN: Verify the results
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("DevOps Pipeline");
        assertThat(result.get(1).getName()).isEqualTo("Cloud Migration");
        verify(projectRepository, times(1)).findAll(); // Confirms repository was called exactly once
    }

    // TEST 2: Find By ID (Happy Path)
    @Test
    void shouldReturnProjectById_WhenFound() {
        // GIVEN: Project exists in fake database
        Project p = new Project("CI/CD Setup", "IN_PROGRESS");
        p.setId(10L);
        when(projectRepository.findById(10L)).thenReturn(Optional.of(p));

        // WHEN: Calling the service method
        ProjectDTO result = projectService.getProjectById(10L);

        // THEN: Verify result matches
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(10L);
        assertThat(result.getName()).isEqualTo("CI/CD Setup");
        assertThat(result.getStatus()).isEqualTo("IN_PROGRESS");
    }

    // TEST 3: Find By ID (Sad Path / Error Handling)
    @Test
    void shouldThrowResourceNotFoundException_WhenProjectNotFound() {
        // GIVEN: Repository returns empty for ID 999
        when(projectRepository.findById(999L)).thenReturn(Optional.empty());

        // WHEN & THEN: Verify the exception is thrown
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            projectService.getProjectById(999L);
        });

        assertThat(exception.getMessage()).isEqualTo("Project not found with id: 999");
    }

    // TEST 4: Add New Project
    @Test
    void shouldSaveProject_WhenAddingNewProject() {
        // GIVEN: Incoming DTO
        ProjectDTO dto = new ProjectDTO(null, "Kubernetes Cluster", "PLANNING");

        // WHEN: Calling addProject
        projectService.addProject(dto);

        // THEN: Verify repository.save() was called with a matching entity
        verify(projectRepository, times(1)).save(any(Project.class));
    }
}
