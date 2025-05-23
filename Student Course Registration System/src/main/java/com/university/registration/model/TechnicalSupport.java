package com.university.registration.model;

public class TechnicalSupport extends Support {
    public TechnicalSupport(int id, String studentName, String message) {
        setId(id);
        setStudentName(studentName);
        setType("Technical");
        setMessage(message);
    }
}