package com.devopshub.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.devopshub.backend.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
