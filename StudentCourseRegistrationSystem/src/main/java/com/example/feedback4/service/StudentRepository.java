package com.example.feedback4.service;

import com.example.feedback4.model.Student;

import java.util.List;

public interface StudentRepository {
    List<Student> findAll();
    void saveAll(List<Student> students);
    Student findById(Long id);
}
