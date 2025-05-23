
        package com.university.registration.controller;

import com.university.registration.model.Enrollment;
import com.university.registration.service.EnrollmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/enrollments")
@CrossOrigin(origins = "*")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping
    public List<Enrollment> getAllEnrollments() {
        return enrollmentService.getAllEnrollments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Enrollment> getEnrollmentById(@PathVariable int id) {
        Enrollment enrollment = enrollmentService.getEnrollmentById(id);
        return ResponseEntity.ok(enrollment);
    }

    @GetMapping("/course/{courseId}")
    public List<Enrollment> getEnrollmentsByCourseId(@PathVariable int courseId) {
        return enrollmentService.getEnrollmentsByCourseId(courseId);
    }

    @GetMapping("/student/{email}")
    public List<Enrollment> getEnrollmentsByStudentEmail(@PathVariable String email) {
        return enrollmentService.getEnrollmentsByStudentEmail(email);
    }

    @PostMapping
    public ResponseEntity<Enrollment> createEnrollment(@Valid @RequestBody Enrollment enrollment) {
        Enrollment createdEnrollment = enrollmentService.createEnrollment(enrollment);
        return ResponseEntity.ok(createdEnrollment);
    }

    @PostMapping("/user-enrollment")
    public ResponseEntity<Enrollment> createUserEnrollment(
            @RequestParam String userEmail,
            @RequestBody Map<String, Object> enrollmentData) {

        int courseId = Integer.parseInt(enrollmentData.get("courseId").toString());
        String paymentMethod = enrollmentData.get("paymentMethod").toString();

        Enrollment createdEnrollment = enrollmentService.createUserEnrollment(userEmail, courseId, paymentMethod);
        return ResponseEntity.ok(createdEnrollment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable int id) {
        enrollmentService.deleteEnrollment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/queue")
    public List<Enrollment> getEnrollmentQueue() {
        return enrollmentService.getEnrollmentQueue();
    }
}
