package com.example.feedback4.model;

public interface AdminOperations {
    int getTotalStudents();
    String generateStudentReport();
    boolean deactivateStudent(Long id);
}