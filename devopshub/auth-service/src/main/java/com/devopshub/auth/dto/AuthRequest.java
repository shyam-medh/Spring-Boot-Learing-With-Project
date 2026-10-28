package com.devopshub.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request payload for user login")
public class AuthRequest {

    @Schema(description = "Username", example = "shyam_lead")
    @NotBlank(message = "Username is required")
    private String username;

    @Schema(description = "Password", example = "supersecret123")
    @NotBlank(message = "Password is required")
    private String password;

    public AuthRequest() {}

    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
