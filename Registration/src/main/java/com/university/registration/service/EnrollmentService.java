package com.university.registration.service;

import com.university.registration.exception.ResourceNotFoundException;
import com.university.registration.model.Enrollment;
import com.university.registration.model.Student;
import com.university.registration.repository.EnrollmentRepository;
import com.university.registration.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseService courseService;

    public EnrollmentService(EnrollmentRepository enrollmentRepository, StudentRepository studentRepository, CourseService courseService) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseService = courseService;
    }

    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    public Enrollment createEnrollment(Enrollment enrollment) {
        // Verify course exists
        courseService.getCourseById(enrollment.getCourseId());

        // Check if student exists, if not create new
        Student existingStudent = studentRepository.findById(enrollment.getStudent().getStudentId())
                .orElse(null);
        if (existingStudent == null) {
            studentRepository.save(enrollment.getStudent());
        }

        return enrollmentRepository.save(enrollment);
    }
}