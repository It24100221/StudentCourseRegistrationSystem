package com.university.registration.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public class User {
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    @JsonProperty
    private String email;

    @NotBlank(message = "Full name is mandatory")
    @JsonProperty
    private String fullName;

    @NotBlank(message = "Password is mandatory")
    @JsonProperty
    private String password;

    @JsonProperty
    private LocalDateTime registrationTime;

    // Constructors
    public User() {
        this.registrationTime = LocalDateTime.now();
    }

    public User(String email, String fullName, String password) {
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.registrationTime = LocalDateTime.now();
    }

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(LocalDateTime registrationTime) {
        this.registrationTime = registrationTime;
    }
}