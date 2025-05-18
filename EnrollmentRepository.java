package com.university.registration.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.university.registration.model.Enrollment;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Repository
public class EnrollmentRepository extends FileRepository<Enrollment> {

    private final Queue<Enrollment> registrationQueue = new LinkedList<>();

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

    public void enqueueRegistrationRequest(Enrollment enrollment) {
        registrationQueue.offer(enrollment);
    }

    public Enrollment processNextRegistrationRequest() {
        Enrollment enrollment = registrationQueue.poll();
        if (enrollment != null) {
            save(enrollment);
        }
        return enrollment;
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