package com.nayana.demo.model;

public class Support {
    private int id;
    private String studentName;
    private String type; // Academic or Technical
    private String message;

    // Constructors
    public Support() {}
    public Support(int id, String studentName, String type, String message) {
        this.id = id;
        this.studentName = studentName;
        this.type = type;
        this.message = message;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
