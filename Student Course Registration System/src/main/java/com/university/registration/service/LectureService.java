package com.university.registration.service;

import com.university.registration.exception.ResourceNotFoundException;
import com.university.registration.model.Lecture;
import com.university.registration.repository.LectureRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LectureService {

    private final LectureRepository lectureRepository;

    public LectureService(LectureRepository lectureRepository) {
        this.lectureRepository = lectureRepository;
    }

    public List<Lecture> getAllLectures() {
        return lectureRepository.findAll();
    }

    public Lecture getLectureById(int id) {
        return lectureRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lecture not found with id: " + id));
    }

    public Lecture createLecture(Lecture lecture) {
        return lectureRepository.save(lecture);
    }

    public Lecture updateLecture(int id, Lecture lecture) {
        if (!lectureRepository.findById(id).isPresent()) {
            throw new ResourceNotFoundException("Lecture not found with id: " + id);
        }
        lecture.setId(id);
        return lectureRepository.save(lecture);
    }

    public void deleteLecture(int id) {
        if (!lectureRepository.findById(id).isPresent()) {
            throw new ResourceNotFoundException("Lecture not found with id: " + id);
        }
        lectureRepository.deleteById(id);
    }
}