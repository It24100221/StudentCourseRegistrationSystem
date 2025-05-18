package com.university.registration.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Course {
    @JsonProperty
    private int id;

    @NotBlank(message = "Title is mandatory")
    @JsonProperty
    private String title;

    @NotBlank(message = "Instructor is mandatory")
    @JsonProperty
    private String instructor;

    @Min(value = 0, message = "Price must be non-negative")
    @JsonProperty
    private double price;

    @Min(value = 1, message = "Duration must be at least 1 week")
    @JsonProperty
    private int duration;

    @NotBlank(message = "Description is mandatory")
    @JsonProperty
    private String description;

    @NotNull(message = "Learning points cannot be null")
    @JsonProperty
    private List<String> learningPoints = new ArrayList<>();

    // Constructors
    public Course() {}

    public Course(int id, String title, String instructor, double price, int duration, String description, List<String> learningPoints) {
        this.id = id;
        this.title = title;
        this.instructor = instructor;
        this.price = price;
        this.duration = duration;
        this.description = description;
        this.learningPoints = learningPoints;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getLearningPoints() {
        return learningPoints;
    }

    public void setLearningPoints(List<String> learningPoints) {
        this.learningPoints = learningPoints;
    }
}