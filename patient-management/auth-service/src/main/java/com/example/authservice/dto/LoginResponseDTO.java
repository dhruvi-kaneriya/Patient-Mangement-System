package com.example.authservice.dto;

public class LoginResponseDTO {
    public LoginResponseDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    private final String token;
}
