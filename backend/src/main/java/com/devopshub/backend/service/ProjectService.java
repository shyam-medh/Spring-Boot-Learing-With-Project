package com.devopshub.backend.service;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.devopshub.backend.dto.ProjectDTO;
import com.devopshub.backend.entity.Project;
import com.devopshub.backend.repository.ProjectRepository;
import com.devopshub.backend.exception.ResourceNotFoundException; // Add this import at the top!

@Service
public class ProjectService {

    // 1. Give the Chef access to the Fridge
    private ProjectRepository repository;

    public ProjectService(ProjectRepository repository) {
        this.repository = repository;
    }

    // 2. READ: Get Ingredients from Fridge, put them on DTO Plates
    public List<ProjectDTO> getAllProjectS() {
        List<Project> entities = repository.findAll(); // Get from database
        List<ProjectDTO> dtos = new ArrayList<>();

        for (Project project : entities) {
            dtos.add(new ProjectDTO(project.getId(), project.getName(), project.getStatus()));
        }
        return dtos;
    }

    // 3. CREATE: Take DTO Plate from UI, turn it into an Ingredient, put in Fridge
    public void addProject(ProjectDTO newProject) {
        Project entity = new Project(newProject.getName(), newProject.getStatus());
        repository.save(entity); // Save to database
    }

    // 4. DELETE: Tell the Fridge to throw it away
    public void deleteProject(Long id) {
        repository.deleteById(id); // Delete from database
    }

    // 5. READ: Get Ingredient from Fridge by ID
    public ProjectDTO getProjectById(Long id) {
        // We ask the Fridge to find it. If it is NOT there, we THROW our custom error!
        Project project = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));
        // If it IS there, we put it on a DTO Plate and return it
        return new ProjectDTO(project.getId(), project.getName(), project.getStatus());
    }
}
