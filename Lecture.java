package com.university.registration.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class Lecture {
    @JsonProperty
    private int id;

    @NotBlank(message = "Course name is mandatory")
    @JsonProperty
    private String course;

    @NotBlank(message = "Lecturer is mandatory")
    @JsonProperty
    private String lecturer;

    @NotBlank(message = "Day is mandatory")
    @JsonProperty
    private String day;

    @NotBlank(message = "Time is mandatory")
    @JsonProperty
    private String time;

    @Min(value = 30, message = "Duration must be at least 30 minutes")
    @JsonProperty
    private int duration;

    @NotBlank(message = "Location is mandatory")
    @JsonProperty
    private String location;

    @JsonProperty
    private String description;

    // Constructors
    public Lecture() {}

    public Lecture(int id, String course, String lecturer, String day, String time, int duration, String location, String description) {
        this.id = id;
        this.course = course;
        this.lecturer = lecturer;
        this.day = day;
        this.time = time;
        this.duration = duration;
        this.location = location;
        this.description = description;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}