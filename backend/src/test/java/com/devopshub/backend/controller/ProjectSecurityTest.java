package com.devopshub.backend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.devopshub.backend.config.SecurityConfig;
import com.devopshub.backend.security.JwtAuthenticationFilter;
import com.devopshub.backend.security.JwtUtil;
import com.devopshub.backend.service.CustomUserDetailsService;
import com.devopshub.backend.service.ProjectService;

import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Phase 7.3: Spring Security Integration Testing
 * Verifies that the security rules in SecurityConfig are enforced over HTTP:
 * 1. Anonymous visitor -> 403 Forbidden
 * 2. Regular USER -> 403 Forbidden (Blocked from admin endpoints)
 * 3. ADMIN -> 200 OK (Allowed through)
 */
@WebMvcTest(ProjectController.class)
@Import({SecurityConfig.class, JwtAuthenticationFilter.class}) // Load our real SecurityConfig rules!
class ProjectSecurityTest {

    @Autowired
    private MockMvc mockMvc; // Fake Postman

    @MockitoBean
    private ProjectService projectService; // Fake Chef

    // Dummy dependencies for JwtAuthenticationFilter
    @MockitoBean
    private JwtUtil jwtUtil;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    // TEST 1: Stranger with NO badge -> Blocked with 403 Forbidden!
    @Test
    void shouldRejectAnonymousUser_WhenDeletingProject() throws Exception {
        mockMvc.perform(delete("/api/projects/1").with(csrf()))
                .andExpect(status().isForbidden());
    }

    // TEST 2: Regular USER badge -> Blocked with 403 Forbidden!
    @Test
    void shouldRejectRegularUser_WhenDeletingProject() throws Exception {
        mockMvc.perform(delete("/api/projects/1")
                        .with(user("shyam").roles("USER"))
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }

    // TEST 3: ADMIN badge -> Allowed through with 200 OK!
    @Test
    void shouldAllowAdmin_WhenDeletingProject() throws Exception {
        doNothing().when(projectService).deleteProject(1L);

        mockMvc.perform(delete("/api/projects/1")
                        .with(user("admin").roles("ADMIN"))
                        .with(csrf()))
                .andExpect(status().isOk());
    }
}
