package model;

public class Course {
    private String code;
    private String name;
    private String duration;

    public Course(String code, String name, String duration) {
        this.code = code;
        this.name = name;
        this.duration = duration;
    }

    // Getters
    public String getCode() { return code; }
    public String getName() { return name; }
    public String getDuration() { return duration; }
}