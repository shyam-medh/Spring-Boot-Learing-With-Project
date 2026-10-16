package com.devopshub.backend.controller;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.devopshub.backend.dto.ProjectDTO;
import com.devopshub.backend.exception.ResourceNotFoundException;
import com.devopshub.backend.security.JwtAuthenticationFilter;
import com.devopshub.backend.security.JwtUtil;
import com.devopshub.backend.service.CustomUserDetailsService;
import com.devopshub.backend.service.ProjectService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProjectController.class) // Boots only the Web Layer for ProjectController
@AutoConfigureMockMvc(addFilters = false) // Disables security filters so we isolate Controller HTTP logic
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc; // The Fake Postman

    private final ObjectMapper objectMapper = new ObjectMapper(); // Helper to turn Java objects into JSON strings

    @MockitoBean
    private ProjectService projectService; // Stunt Double Chef

    // Satisfy SecurityConfig constructor/filter dependencies in the test context
    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockitoBean
    private JwtUtil jwtUtil;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    // TEST 1: GET /api/projects (Happy Path)
    @Test
    void shouldReturnAllProjects_WhenGetRequestMade() throws Exception {
        // GIVEN: Service returns 2 projects
        ProjectDTO p1 = new ProjectDTO(1L, "DevOps Pipeline", "ACTIVE");
        ProjectDTO p2 = new ProjectDTO(2L, "Cloud Migration", "COMPLETED");
        when(projectService.getAllProjectS()).thenReturn(List.of(p1, p2));

        // WHEN & THEN: Perform GET /api/projects and verify JSON
        mockMvc.perform(get("/api/projects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("DevOps Pipeline"))
                .andExpect(jsonPath("$[1].name").value("Cloud Migration"));
    }

    // TEST 2: GET /api/projects/{id} (Found)
    @Test
    void shouldReturnProjectById_WhenProjectExists() throws Exception {
        // GIVEN: Service returns project #10
        ProjectDTO p = new ProjectDTO(10L, "Kubernetes Deploy", "ACTIVE");
        when(projectService.getProjectById(10L)).thenReturn(p);

        // WHEN & THEN: Perform GET /api/projects/10
        mockMvc.perform(get("/api/projects/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.name").value("Kubernetes Deploy"))
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

    // TEST 3: GET /api/projects/{id} (Not Found -> 404)
    @Test
    void shouldReturn404_WhenProjectNotFound() throws Exception {
        // GIVEN: Service throws ResourceNotFoundException
        when(projectService.getProjectById(999L))
                .thenThrow(new ResourceNotFoundException("Project not found with id: 999"));

        // WHEN & THEN: Perform GET /api/projects/999 and expect 404
        mockMvc.perform(get("/api/projects/999"))
                .andExpect(status().isNotFound());
    }

    // TEST 4: POST /api/projects (Valid Input -> 200 OK)
    @Test
    void shouldCreateProject_WhenValidInputProvided() throws Exception {
        // GIVEN: Valid DTO payload
        ProjectDTO validDto = new ProjectDTO(null, "Terraform Infra", "PLANNING");
        doNothing().when(projectService).addProject(any(ProjectDTO.class));

        // WHEN & THEN: Perform POST /api/projects with JSON
        mockMvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Project created successfully!"));
    }

    // TEST 5: POST /api/projects (Invalid Input -> 400 Bad Request)
    @Test
    void shouldReturn400BadRequest_WhenValidationFails() throws Exception {
        // GIVEN: Invalid DTO (blank name and status)
        ProjectDTO invalidDto = new ProjectDTO(null, "", "");

        // WHEN & THEN: Perform POST /api/projects and expect 400 Bad Request
        mockMvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }

    // TEST 6: DELETE /api/projects/{id} (Happy Path)
    @Test
    void shouldDeleteProject_WhenDeleteRequestMade() throws Exception {
        // GIVEN: Service deletes without errors
        doNothing().when(projectService).deleteProject(5L);

        // WHEN & THEN: Perform DELETE /api/projects/5
        mockMvc.perform(delete("/api/projects/5"))
                .andExpect(status().isOk())
                .andExpect(content().string("Project deleted successfully"));
    }
}
