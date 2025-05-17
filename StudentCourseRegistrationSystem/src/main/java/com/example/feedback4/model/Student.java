package com.example.feedback4.model;

public class Student {

    private Long Id;
    private String firstName;
    private String LastName;
    private String email;
    private String password;
    private boolean active = true; // New field, default is true

    public Student() {
    }

    public Student(Long id, String firstName, String lastName, String email, String password) {
        Id = id;
        this.firstName = firstName;
        LastName = lastName;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}