package com.example.feedback4.model;

public class Admin extends UserRole implements AdminOperations {
    private final StudentService studentService;
    private final String adminName;

    public Admin(StudentService studentService, String adminName) {
        super("Admin");
        this.studentService = studentService;
        this.adminName = adminName != null ? adminName : "DefaultAdmin";
    }

    @Override
    public int getTotalStudents() {
        return studentService.getAllStudents().size();
    }

    @Override
    public String generateStudentReport() {
        System.out.println("Student Report by Admin: " + adminName);
        System.out.println("Total Students: " + getTotalStudents());
        studentService.getAllStudents().forEach(student ->
                System.out.println("ID: " + student.getId() + ", Name: " + student.getFirstName() + " " + student.getLastName()));
        return null;
    }

    @Override
    public boolean deactivateStudent(Long id) {
        Student student = studentService.getStudentById(id);
        if (student != null) {
            studentService.deleteStudentById(id);
            return true;
        }
        return false;
    }

    @Override
    public String getRoleDescription() {
        return "Administrator with full access to manage student records.";
    }

    public String getAdminName() {
        return adminName;
    }

    public void exportStudentReportToExcel(String filePath) {
        // Use Apache POI to create an Excel file
        // Example implementation can be added based on your needs
    }
}