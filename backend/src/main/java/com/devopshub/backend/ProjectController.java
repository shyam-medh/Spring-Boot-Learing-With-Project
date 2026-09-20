package com.devopshub.backend;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public String createProject(@RequestBody ProjectDTO project) {
        projectService.addProject(project);
        return "Project created successfully!";
    }
}
