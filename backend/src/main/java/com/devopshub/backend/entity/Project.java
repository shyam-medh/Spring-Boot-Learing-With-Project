package com.devopshub.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "projects") // This tells Spring to name the database table "projects"
public class Project {

    @Id // This tells Spring this is the Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // This tells Postgres to auto-increment the ID (1, 2, 3...)
    private Long id;

    private String name;
    private String status;

    // Empty constructor is required for JPA
    public Project() {
    }

    public Project(String name, String status) {
        this.name = name;
        this.status = status;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
