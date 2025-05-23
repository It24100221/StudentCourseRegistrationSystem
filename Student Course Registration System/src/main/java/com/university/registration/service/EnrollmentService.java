package com.university.registration.service;

import com.university.registration.exception.ResourceNotFoundException;
import com.university.registration.model.Course;
import com.university.registration.model.Enrollment;
import com.university.registration.model.EnrollmentQueue;
import com.university.registration.model.Student;
import com.university.registration.model.User;
import com.university.registration.repository.EnrollmentRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final CourseService courseService;
    private final UserService userService;
    private final EnrollmentQueue enrollmentQueue;

    public EnrollmentService(EnrollmentRepository enrollmentRepository, CourseService courseService, UserService userService) {
        this.enrollmentRepository = enrollmentRepository;
        this.courseService = courseService;
        this.userService = userService;
        this.enrollmentQueue = new EnrollmentQueue();

        // Initialize the queue with existing enrollments
        List<Enrollment> existingEnrollments = enrollmentRepository.findAll();
        for (Enrollment enrollment : existingEnrollments) {
            boolean success = enrollmentQueue.enqueue(enrollment);
            if (!success) {
                // Queue is full, stop adding more enrollments
                break;
            }
        }
    }

    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    public Enrollment getEnrollmentById(int id) {
        return enrollmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found with id: " + id));
    }

    public List<Enrollment> getEnrollmentsByCourseId(int courseId) {
        return enrollmentRepository.findByCourseId(courseId);
    }

    public List<Enrollment> getEnrollmentsByStudentEmail(String email) {
        return enrollmentRepository.findByStudentEmail(email);
    }

    public Enrollment createEnrollment(Enrollment enrollment) {
        // Verify that the course exists
        Course course = courseService.getCourseById(enrollment.getCourseId());

        // Save the enrollment
        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);

        // Add to the queue using insertion sort
        boolean success = enrollmentQueue.enqueue(savedEnrollment);
        // Even if the queue is full, we still return the saved enrollment
        // The enrollment is saved in the database regardless of the queue status

        return savedEnrollment;
    }

    public Enrollment createUserEnrollment(String userEmail, int courseId, String paymentMethod) {
        // Get the user
        User user = userService.getUserByEmail(userEmail);

        // Convert user to student
        Student student = new Student(userEmail, user.getFullName(), user.getEmail());

        // Create enrollment
        Enrollment enrollment = new Enrollment();
        enrollment.setCourseId(courseId);
        enrollment.setStudent(student);
        enrollment.setPaymentMethod(paymentMethod);

        return createEnrollment(enrollment);
    }

    public void deleteEnrollment(int id) {
        // Verify that the enrollment exists
        getEnrollmentById(id);

        // Delete the enrollment
        enrollmentRepository.deleteById(id);

        // Rebuild the enrollment queue to ensure consistency
        enrollmentQueue.clear();
        List<Enrollment> remainingEnrollments = enrollmentRepository.findAll();
        for (Enrollment enrollment : remainingEnrollments) {
            boolean success = enrollmentQueue.enqueue(enrollment);
            if (!success) {
                // Queue is full, stop adding more enrollments
                break;
            }
        }
    }

    public List<Enrollment> getEnrollmentQueue() {
        // Convert array to List for controller compatibility
        return Arrays.asList(enrollmentQueue.getAll());
    }
}
