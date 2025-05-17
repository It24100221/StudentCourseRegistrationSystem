package com.university.registration.controller;

import com.university.registration.model.Lecture;
import com.university.registration.service.LectureService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lectures")
@CrossOrigin(origins = "*")
public class LectureController {

    private final LectureService lectureService;

    public LectureController(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    @GetMapping
    public List<Lecture> getAllLectures() {
        return lectureService.getAllLectures();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lecture> getLectureById(@PathVariable int id) {
        Lecture lecture = lectureService.getLectureById(id);
        return ResponseEntity.ok(lecture);
    }

    @PostMapping
    public ResponseEntity<Lecture> createLecture(@Valid @RequestBody Lecture lecture) {
        Lecture createdLecture = lectureService.createLecture(lecture);
        return ResponseEntity.ok(createdLecture);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Lecture> updateLecture(@PathVariable int id, @Valid @RequestBody Lecture lecture) {
        Lecture updatedLecture = lectureService.updateLecture(id, lecture);
        return ResponseEntity.ok(updatedLecture);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLecture(@PathVariable int id) {
        lectureService.deleteLecture(id);
        return ResponseEntity.noContent().build();
    }
}