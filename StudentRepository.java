package com.university.registration.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.university.registration.model.Student;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class StudentRepository extends FileRepository<Student> {

    public StudentRepository() {
        super("students.txt");
    }

    @Override
    protected TypeReference<List<Student>> getTypeReference() {
        return new TypeReference<>() {};
    }

    public List<Student> findAll() {
        return readAll();
    }

    public Optional<Student> findById(String studentId) {
        return readAll().stream().filter(student -> student.getStudentId().equals(studentId)).findFirst();
    }

    public Student save(Student student) {
        List<Student> students = readAll();
        students.removeIf(s -> s.getStudentId().equals(student.getStudentId()));
        students.add(student);
        saveAll(students);
        return student;
    }
}