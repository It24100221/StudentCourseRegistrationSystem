package com.university.registration.model;

import lombok.Data;

@Data
public class Support {
    private int id;
    private String studentName;
    private String type; // Academic or Technical
    private String message;
}