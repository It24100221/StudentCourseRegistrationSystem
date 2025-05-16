package com.example.feedback4.model;

public abstract class UserRole {
    private final String roleName;

    protected UserRole(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public abstract String getRoleDescription();
}