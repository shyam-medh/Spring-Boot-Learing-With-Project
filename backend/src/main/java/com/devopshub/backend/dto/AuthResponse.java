package com.devopshub.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response containing the issued JSON Web Token")
public class AuthResponse {

    @Schema(description = "Cryptographically signed JWT Bearer token", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY...")
    private String token;

    public AuthResponse() {
    }

    public AuthResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
