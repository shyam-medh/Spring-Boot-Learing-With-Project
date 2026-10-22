package com.devopshub.backend.integration;

import java.util.List;
import java.util.TimeZone;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.devopshub.backend.dto.ProjectDTO;
import com.devopshub.backend.entity.Project;
import com.devopshub.backend.repository.ProjectRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Phase 7.4: Full End-to-End Integration Testing with @SpringBootTest
 * NO MOCKS / NO STUNT DOUBLES.
 * Boots the entire application context and connects directly to the real PostgreSQL database in Docker!
 */
@SpringBootTest // Full Engine: Boots entire Spring Boot App & connects to PostgreSQL
@AutoConfigureMockMvc // Provides MockMvc connected to the real filter chain and controllers
class ProjectIntegrationTest {

    static {
        // Enforce UTC timezone before Spring context boots so Postgres does not reject Asia/Calcutta on Windows
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Autowired
    private MockMvc mockMvc; // Fake Postman connected to the real app

    @Autowired
    private ProjectRepository projectRepository; // Real Database Access!

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void cleanDatabase() {
        // Clear project table before each test to guarantee fresh, isolated results
        projectRepository.deleteAll();
    }

    // TEST 1: Full Create & Read Journey (End-to-End)
    @Test
    void shouldCreateAndRetrieveProject_EndToEnd() throws Exception {
        // STEP 1: Admin sends POST /api/projects with valid JSON
        ProjectDTO newProject = new ProjectDTO(null, "Kubernetes Migration", "IN_PROGRESS");

        mockMvc.perform(post("/api/projects")
                        .with(user("admin").roles("ADMIN"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newProject)))
                .andExpect(status().isOk())
                .andExpect(content().string("Project created successfully!"));

        // STEP 2: VERIFY DIRECTLY IN POSTGRESQL!
        // Query the real database to prove the row was genuinely inserted
        List<Project> databaseRows = projectRepository.findAll();
        assertThat(databaseRows).hasSize(1);
        assertThat(databaseRows.get(0).getName()).isEqualTo("Kubernetes Migration");
        assertThat(databaseRows.get(0).getStatus()).isEqualTo("IN_PROGRESS");

        // STEP 3: User reads the project back over HTTP (GET /api/projects)
        mockMvc.perform(get("/api/projects")
                        .with(user("shyam").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Kubernetes Migration"))
                .andExpect(jsonPath("$[0].status").value("IN_PROGRESS"));
    }

    // TEST 2: Full Delete Journey (End-to-End)
    @Test
    void shouldDeleteProject_EndToEnd() throws Exception {
        // STEP 1: Pre-populate a real project into PostgreSQL
        Project saved = projectRepository.save(new Project("Legacy Monolith", "DEPRECATED"));
        Long projectId = saved.getId();

        // STEP 2: Admin deletes the project over HTTP
        mockMvc.perform(delete("/api/projects/" + projectId)
                        .with(user("admin").roles("ADMIN"))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("Project deleted successfully"));

        // STEP 3: VERIFY DIRECTLY IN POSTGRESQL!
        // The project must no longer exist in the database table
        assertThat(projectRepository.findById(projectId)).isEmpty();
    }
}
