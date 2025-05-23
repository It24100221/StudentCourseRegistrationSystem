package com.university.registration.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.university.registration.model.Enrollment;
import com.university.registration.model.EnrollmentQueue;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class EnrollmentRepository extends FileRepository<Enrollment> {

    private final EnrollmentQueue enrollmentQueue = new EnrollmentQueue();

    public EnrollmentRepository() {
        super("enrollments.txt");

        // Initialize queue with recent enrollments from file
        List<Enrollment> all = readAll();
        all.stream()
                .sorted((a, b) -> Long.compare(a.getTimestamp(), b.getTimestamp()))
                .limit(10)
                .forEach(enrollmentQueue::enqueue);
    }

    @Override
    protected TypeReference<List<Enrollment>> getTypeReference() {
        return new TypeReference<>() {};
    }

    public List<Enrollment> findAll() {
        return readAll();
    }

    public Optional<Enrollment> findById(int id) {
        return readAll().stream()
                .filter(enrollment -> enrollment.getId() == id)
                .findFirst();
    }

    public List<Enrollment> findByCourseId(int courseId) {
        return readAll().stream()
                .filter(enrollment -> enrollment.getCourseId() == courseId)
                .collect(Collectors.toList());
    }

    public List<Enrollment> findByStudentEmail(String email) {
        return readAll().stream()
                .filter(enrollment -> enrollment.getStudent().getEmail().equals(email))
                .collect(Collectors.toList());
    }

    public Enrollment save(Enrollment enrollment) {
        List<Enrollment> enrollments = readAll();

        // Generate new ID if not provided
        if (enrollment.getId() == 0) {
            int maxId = enrollments.stream()
                    .mapToInt(Enrollment::getId)
                    .max()
                    .orElse(0);
            enrollment.setId(maxId + 1);
        }

        // Update existing enrollment or add new one
        boolean updated = false;
        for (int i = 0; i < enrollments.size(); i++) {
            if (enrollments.get(i).getId() == enrollment.getId()) {
                enrollments.set(i, enrollment);
                updated = true;
                break;
            }
        }

        if (!updated) {
            enrollments.add(enrollment);
        }

        // Save to file
        saveAll(enrollments);

        // Also enqueue into recent enrollment queue
        enrollmentQueue.enqueue(enrollment);

        return enrollment;
    }

    public void deleteById(int id) {
        List<Enrollment> enrollments = readAll();
        enrollments.removeIf(enrollment -> enrollment.getId() == id);
        saveAll(enrollments);
    }

    public List<Enrollment> getRecentEnrollmentsQueue() {
        return List.of(enrollmentQueue.getAll());
    }

    public void clearQueue() {
        enrollmentQueue.clear();
    }



    public int getQueueSize() {
        return enrollmentQueue.size();
    }
}
