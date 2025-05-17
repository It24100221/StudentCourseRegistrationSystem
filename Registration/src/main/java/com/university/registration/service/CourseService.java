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

    public Course updateCourse(int id, Course course) {
        if (!courseRepository.findById(id).isPresent()) {
            throw new ResourceNotFoundException("Course not found with id: " + id);
        }
        course.setId(id);
        return courseRepository.save(course);
    }

    public void deleteCourse(int id) {
        if (!courseRepository.findById(id).isPresent()) {
            throw new ResourceNotFoundException("Course not found with id: " + id);
        }
        courseRepository.deleteById(id);
    }

    public List<Course> findByTitleContaining(String title) {
        return null;
    }

    public List<Course> findByInstructorContaining(String instructor) {
        return null;
    }

    public List<Course> findByPriceLessThanEqual(double maxPrice) {
        return null;
    }
}