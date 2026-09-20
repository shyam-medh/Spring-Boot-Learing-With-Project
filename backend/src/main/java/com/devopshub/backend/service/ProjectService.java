package com.devopshub.backend.service;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.devopshub.backend.dto.ProjectDTO;

@Service
public class ProjectService {

    public ProjectService() {
        projects.add(new ProjectDTO(1L, "AWS Migration", "In Progress"));
        projects.add(new ProjectDTO(2L, "Kubernetes Setup", "In Progress"));
        projects.add(new ProjectDTO(3L, "CI/CD Pipeline", "In Progress"));
    }

    private List<ProjectDTO> projects = new ArrayList<>();

    public List<ProjectDTO> getAllProjectS() {
        return projects;
    }

    public void addProject(ProjectDTO newProject) {
        projects.add(newProject);
    }
}
