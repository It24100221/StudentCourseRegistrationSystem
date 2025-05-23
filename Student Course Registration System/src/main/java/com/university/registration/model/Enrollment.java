
package com.university.registration.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class Enrollment {
    @JsonProperty
    private int id;

    @JsonProperty
    private int courseId;

    @NotNull(message = "Student is mandatory")
    @Valid
    @JsonProperty
    private Student student;

    @NotBlank(message = "Payment method is mandatory")
    @JsonProperty
    private String paymentMethod;

    @JsonProperty
    private long timestamp; // Added for enrollment time

    // Constructors
    public Enrollment() {
        this.timestamp = System.currentTimeMillis(); // Set timestamp on creation
    }

    public Enrollment(int id, int courseId, Student student, String paymentMethod) {
        this.id = id;
        this.courseId = courseId;
        this.student = student;
        this.paymentMethod = paymentMethod;
        this.timestamp = System.currentTimeMillis(); // Set timestamp on creation
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
