package com.example.feedback4.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Feedback {
    private Long id;
    private String studentName;
    private String courseName;
    private Integer rating;
    private String comments;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
}