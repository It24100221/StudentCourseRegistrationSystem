package com.university.registration.service;

import com.university.registration.exception.ResourceNotFoundException;
import com.university.registration.model.Course;
import com.university.registration.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseById(int id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
    }

    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    public List<Course> findByTitleContaining(String title) {
        return courseRepository.findAll().stream()
                .filter(course -> course.getTitle().toLowerCase().contains(title.toLowerCase()))
                .toList();
    }

    public List<Course> findByInstructorContaining(String instructor) {
        return courseRepository.findAll().stream()
                .filter(course -> course.getInstructor().toLowerCase().contains(instructor.toLowerCase()))
                .toList();
    }

    public List<Course> findByPriceLessThanEqual(double maxPrice) {
        return courseRepository.findAll().stream()
                .filter(course -> course.getPrice() <= maxPrice)
                .toList();
    }
}