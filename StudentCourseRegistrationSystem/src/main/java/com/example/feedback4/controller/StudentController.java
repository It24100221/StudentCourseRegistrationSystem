package com.example.feedback4.controller;

import com.example.feedback4.service.StudentService;
import com.example.feedback4.model.Admin;
import com.example.feedback4.model.Student;
import com.example.feedback4.model.UserRole;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class StudentController {

    private final StudentService studentService;
    private final Admin admin;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
        this.admin = new Admin(studentService, "SystemAdmin");
    }

    @GetMapping("/home")
    public String home() {
        return "index";
    }

    @GetMapping("/student")
    public String listStudent(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        return "student";
    }

    @GetMapping("/students/new")
    public String createStudentForm(Model model) {
        Student student = new Student();
        model.addAttribute("student", student);
        return "create_student";
    }

    @PostMapping("/student")
    public RedirectView saveStudent(@ModelAttribute("student") Student student) {
        studentService.saveStudent(student);
        return new RedirectView("/home"); // Redirect to home instead of student list
    }

    @GetMapping("/student/edit/{id}")
    public String editStudentForm(@PathVariable Long id, Model model) {
        Student student = studentService.getStudentById(id);
        if (student == null) {
            return "redirect:/student"; // Redirect if student not found
        }
        model.addAttribute("student", student);
        return "edit_student";
    }

    @PostMapping("/student/{id}")
    public RedirectView updateStudent(@PathVariable Long id,
                                      @ModelAttribute("student") Student student) {
        Student existingStudent = studentService.getStudentById(id);
        if (existingStudent == null) {
            return new RedirectView("/student"); // Redirect if student not found
        }
        existingStudent.setId(id);
        existingStudent.setFirstName(student.getFirstName());
        existingStudent.setLastName(student.getLastName());
        existingStudent.setEmail(student.getEmail());
        studentService.updateStudent(existingStudent);
        return new RedirectView("/student");
    }

    @DeleteMapping("/student/{id}")
    public RedirectView deleteStudent(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);
        if (student == null) {
            return new RedirectView("/student");
        }
        studentService.deleteStudentById(id);
        return new RedirectView("/student", true);
    }

    @GetMapping("/student/total")
    @ResponseBody
    public int getTotalStudents() {
        return admin.getTotalStudents();
    }

    @GetMapping("/student/report")
    @ResponseBody
    public String generateStudentReport() {
        admin.generateStudentReport();
        return "Report generated. Check console for details.";
    }

    @PostMapping("/student/deactivate/{id}")
    public RedirectView deactivateStudent(@PathVariable Long id) {
        boolean deactivated = admin.deactivateStudent(id);
        return new RedirectView("/student");
    }

    @GetMapping("/admin/role")
    @ResponseBody
    public String getAdminRoleDescription() {
        return admin.getRoleDescription();
    }

    @GetMapping("/profile")
    public String showProfile(Model model) {
        List<Student> students = studentService.getAllStudents();
        if (!students.isEmpty()) {
            Student student = students.get(students.size() - 1); // Last registered student
            model.addAttribute("student", student);
        } else {
            model.addAttribute("student", new Student()); // Default empty student
        }
        return "profile";
    }
}