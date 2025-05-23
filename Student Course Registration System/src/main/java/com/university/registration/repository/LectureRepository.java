package com.university.registration.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.university.registration.model.Lecture;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class LectureRepository extends FileRepository<Lecture> {

    public LectureRepository() {
        super("lectures.txt");
    }

    @PostConstruct
    public void init() {
        List<Lecture> initialLectures = new ArrayList<>();
        initialLectures.add(new Lecture(1, "Introduction to Programming", "Dr. Kaveen perera", "Monday", "09:00", 90, "Building A, Room 101", "Introduction to programming concepts"));
        initialLectures.add(new Lecture(2, "Data Structures", "Prof. senuk Damhiru", "Tuesday", "11:00", 120, "Building B, Room 205", "Arrays and linked lists"));
        initialLectures.add(new Lecture(3, "Introduction to Programming", "Dr. binol Dodangoda", "Wednesday", "14:00", 60, "Building C, Room 310", "Control structures"));
        initialLectures.add(new Lecture(4, "Data Structures", "Prof. nadu amanda", "Thursday", "10:00", 90, "Building A, Room 102", "Sorting algorithms"));
        saveAll(initialLectures);
    }

    @Override
    protected TypeReference<List<Lecture>> getTypeReference() {
        return new TypeReference<>() {};
    }

    public List<Lecture> findAll() {
        return readAll();
    }

    public Optional<Lecture> findById(int id) {
        return readAll().stream().filter(lecture -> lecture.getId() == id).findFirst();
    }

    public Lecture save(Lecture lecture) {
        List<Lecture> lectures = readAll();
        if (lecture.getId() == 0) {
            int newId = lectures.stream().mapToInt(Lecture::getId).max().orElse(0) + 1;
            lecture.setId(newId);
            lectures.add(lecture);
        } else {
            lectures.removeIf(l -> l.getId() == lecture.getId());
            lectures.add(lecture);
        }
        saveAll(lectures);
        return lecture;
    }

    public void deleteById(int id) {
        List<Lecture> lectures = readAll();
        lectures.removeIf(lecture -> lecture.getId() == id);
        saveAll(lectures);
    }
}