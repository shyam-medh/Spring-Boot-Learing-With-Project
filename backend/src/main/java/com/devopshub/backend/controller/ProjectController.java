package com.devopshub.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.validation.Valid;
import com.devopshub.backend.dto.*;

import com.devopshub.backend.service.ProjectService;

@RequestMapping("api/projects")
@RestController
public class ProjectController {

    private ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping()
    public List<ProjectDTO> getAllProject() {
        return projectService.getAllProjectS();
    }

    @PostMapping()
    public String createProject(@Valid @RequestBody ProjectDTO project) {
        projectService.addProject(project);
        return "Project created successfully!";
    }

    @DeleteMapping("/{id}")
    public String deleteProject(@PathVariable("id") Long id) {
        projectService.deleteProject(id);
        return "Project deleted successfully";
    }

    @GetMapping("/{id}")
    public ProjectDTO getProjectById(@PathVariable("id") Long id) {
        return projectService.getProjectById(id);
    }

}
