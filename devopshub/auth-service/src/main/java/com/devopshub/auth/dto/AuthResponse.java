package com.devopshub.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response payload containing the signed JWT Bearer token and user details")
public class AuthResponse {

    @Schema(description = "Signed JWT token string", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;

    @Schema(description = "Token type", example = "Bearer")
    private String tokenType = "Bearer";

    @Schema(description = "Authenticated username", example = "shyam_lead")
    private String username;

    @Schema(description = "Assigned user role", example = "ADMIN")
    private String role;

    public AuthResponse() {}

    public AuthResponse(String token, String username, String role) {
        this.token = token;
        this.username = username;
        this.role = role;
    }

    public String getToken() { return token; }
    public String getTokenType() { return tokenType; }
    public String getUsername() { return username; }
    public String getRole() { return role; }
}
