package com.university.registration.controller;

import com.university.registration.model.Course;
import com.university.registration.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin(origins = "*")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable int id) {
        Course course = courseService.getCourseById(id);
        return ResponseEntity.ok(course);
    }

    @PostMapping
    public ResponseEntity<Course> createCourse(@Valid @RequestBody Course course) {
        Course createdCourse = courseService.createCourse(course);
        return ResponseEntity.ok(createdCourse);
    }


    @GetMapping("/search/title/{title}")
    public List<Course> searchByTitle(@PathVariable String title) {
        return courseService.findByTitleContaining(title);
    }

    @GetMapping("/search/instructor/{instructor}")
    public List<Course> searchByInstructor(@PathVariable String instructor) {
        return courseService.findByInstructorContaining(instructor);
    }

    @GetMapping("/search/price")
    public List<Course> searchByMaxPrice(@RequestParam double maxPrice) {
        return courseService.findByPriceLessThanEqual(maxPrice);
    }
}
