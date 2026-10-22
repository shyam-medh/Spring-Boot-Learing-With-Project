package com.devopshub.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Credentials for user login")
public class AuthRequest {

    @Schema(description = "Account username", example = "admin_user")
    @NotBlank(message = "Username cannot be empty")
    private String username;

    @Schema(description = "Account password", example = "adminPass123")
    @NotBlank(message = "Password cannot be empty")
    private String password;

    public AuthRequest() {
    }

    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
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
}
