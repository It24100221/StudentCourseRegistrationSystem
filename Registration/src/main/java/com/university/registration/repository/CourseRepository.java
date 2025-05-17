package com.university.registration.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.university.registration.model.Course;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CourseRepository extends FileRepository<Course> {

    public CourseRepository() {
        super("courses.txt");
    }

    @PostConstruct
    public void init() {
        List<Course> initialCourses = new ArrayList<>();
        List<String> learningPoints1 = List.of("Understand programming basics", "Learn Python syntax");
        initialCourses.add(new Course(1, "Introduction to Programming", "Dr. John Smith", 199.99, 12, "Learn the basics of programming with Python.", learningPoints1));
        List<String> learningPoints2 = List.of("Master data structures", "Implement algorithms");
        initialCourses.add(new Course(2, "Data Structures", "Prof. Jane Doe", 249.99, 16, "Explore advanced data structures and algorithms.", learningPoints2));
        saveAll(initialCourses);
    }

    @Override
    protected TypeReference<List<Course>> getTypeReference() {
        return new TypeReference<>() {};
    }

    public List<Course> findAll() {
        return readAll();
    }

    public Optional<Course> findById(int id) {
        return readAll().stream().filter(course -> course.getId() == id).findFirst();
    }

    public Course save(Course course) {
        List<Course> courses = readAll();
        if (course.getId() == 0) {
            int newId = courses.stream().mapToInt(Course::getId).max().orElse(0) + 1;
            course.setId(newId);
            courses.add(course);
        } else {
            courses.removeIf(c -> c.getId() == course.getId());
            courses.add(course);
        }
        saveAll(courses);
        return course;
    }

    public void deleteById(int id) {
        List<Course> courses = readAll();
        courses.removeIf(course -> course.getId() == id);
        saveAll(courses);
    }
}