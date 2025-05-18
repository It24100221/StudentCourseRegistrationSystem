package com.university.registration.service;

import com.university.registration.model.Student;
import com.university.registration.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(String studentId) {
        return studentRepository.findById(studentId)
                .orElse(null);
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }
}