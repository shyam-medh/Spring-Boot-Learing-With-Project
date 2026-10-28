package com.devopshub.auth.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Domain Entity: Represents a registered user with salted BCrypt password hash and security role.
 * Stored in PostgreSQL database: devopshub_auth.
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role = "USER";

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Caption: PrePersist Lifecycle Hook
     * Sets the creation timestamp immediately prior to INSERT.
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    /**
     * Caption: Default No-Args Constructor
     * Required by Hibernate for reflection-based entity instantiation.
     */
    public User() {}

    /**
     * Caption: Parameterized Domain Constructor
     * Used by the service layer to construct new user records with encoded passwords.
     */
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role != null ? role : "USER";
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
