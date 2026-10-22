package com.devopshub.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Details required for registering a new user")
public class RegisterRequest {

    @Schema(description = "Desired unique username", example = "shyam_admin")
    @NotBlank(message = "Username cannot be empty")
    private String username;

    @Schema(description = "Secure password for the account", example = "DevOpsSecPass!2026")
    @NotBlank(message = "Password cannot be empty")
    private String password;

    @Schema(description = "Assigned user role (e.g. 'USER' or 'ADMIN'). Defaults to 'USER' if omitted.", example = "ADMIN")
    private String role;

    public RegisterRequest() {
    }

    public RegisterRequest(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
