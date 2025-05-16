package com.example.feedback4.service;

public interface AuthService {
    boolean login(String email);
    void logout(String email);
    boolean isLoggedIn(String email);
}