package com.university.registration.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.university.registration.model.Enrollment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EnrollmentRepository extends FileRepository<Enrollment> {

    public EnrollmentRepository() {
        super("enrollments.txt");
    }

    @Override
    protected TypeReference<List<Enrollment>> getTypeReference() {
        return new TypeReference<>() {};
    }

    public List<Enrollment> findAll() {
        return readAll();
    }

    public Enrollment save(Enrollment enrollment) {
        List<Enrollment> enrollments = readAll();
        int newId = enrollments.stream().mapToInt(Enrollment::getId).max().orElse(0) + 1;
        enrollment.setId(newId);
        enrollments.add(enrollment);
        saveAll(enrollments);
        return enrollment;
    }
}