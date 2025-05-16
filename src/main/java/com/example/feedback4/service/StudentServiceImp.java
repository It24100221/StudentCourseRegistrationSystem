package com.example.feedback4.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImp implements StudentService {

    private static final String EXCEL_FILE_PATH = "students.xlsx";

    @Override
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        File file = new File(EXCEL_FILE_PATH);

        if (!file.exists()) {
            return students; // Return empty list if file doesn't exist
        }

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheet("Students");

            if (sheet == null) {
                return students; // Return empty list if sheet doesn't exist
            }

            // Skip the header row
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    Student student = new Student();
                    student.setId((long) row.getCell(0).getNumericCellValue());
                    student.setFirstName(getCellStringValue(row.getCell(1)));
                    student.setLastName(getCellStringValue(row.getCell(2)));
                    student.setEmail(getCellStringValue(row.getCell(3)));
                    student.setPassword(getCellStringValue(row.getCell(4)));
                    students.add(student);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading students from Excel: " + e.getMessage());
        }
        return students;
    }

    @Override
    public Student saveStudent(Student student) {
        List<Student> students = getAllStudents();
        if (student.getId() == null) {
            // Assign a new ID (mimic auto-increment)
            long newId = students.isEmpty() ? 1 : students.get(students.size() - 1).getId() + 1;
            student.setId(newId);
        } else {
            // Remove existing student with the same ID (for updates)
            students.removeIf(s -> s.getId().equals(student.getId()));
        }
        students.add(student);
        writeToExcel(students);
        return student;
    }

    @Override
    public Student getStudentById(Long id) {
        return getAllStudents().stream()
                .filter(student -> student.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Student updateStudent(Student student) {
        List<Student> students = getAllStudents();
        boolean found = false;
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId().equals(student.getId())) {
                students.set(i, student);
                found = true;
                break;
            }
        }
        if (!found) {
            throw new RuntimeException("Student not found with id: " + student.getId());
        }
        writeToExcel(students);
        return student;
    }

    @Override
    public void deleteStudentById(Long id) {
        List<Student> students = getAllStudents();
        boolean removed = students.removeIf(student -> student.getId().equals(id));
        if (!removed) {
            throw new RuntimeException("Student not found with id: " + id);
        }
        writeToExcel(students);
    }

    private void writeToExcel(List<Student> students) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Students");

            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("First Name");
            headerRow.createCell(2).setCellValue("Last Name");
            headerRow.createCell(3).setCellValue("Email");
            headerRow.createCell(4).setCellValue("Password");

            // Write student data
            int rowNum = 1;
            for (Student student : students) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(student.getId());
                row.createCell(1).setCellValue(student.getFirstName());
                row.createCell(2).setCellValue(student.getLastName());
                row.createCell(3).setCellValue(student.getEmail());
                row.createCell(4).setCellValue(student.getPassword());
            }

            try (FileOutputStream fos = new FileOutputStream(EXCEL_FILE_PATH)) {
                workbook.write(fos);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing students to Excel: " + e.getMessage());
        }
    }

    // Helper method to handle null or different cell types
    private String getCellStringValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf((int) cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            default -> "";
        };
    }
}