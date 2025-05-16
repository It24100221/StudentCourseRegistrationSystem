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
public class AuthServiceImp implements AuthService {

    private static final String SESSION_FILE_PATH = "sessions.xlsx";
    private final StudentService studentService;

    public AuthServiceImp(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public boolean login(String email) {
        Student student = studentService.getAllStudents().stream()
                .filter(s -> s.getEmail().equals(email))
                .findFirst()
                .orElse(null);

        if (student == null) {
            return false;
        }

        if (isLoggedIn(email)) {
            return true;
        }

        List<String> sessions = getSessions();
        sessions.add(email);
        writeSessionsToExcel(sessions);
        return true;
    }

    @Override
    public void logout(String email) {
        List<String> sessions = getSessions();
        sessions.remove(email);
        writeSessionsToExcel(sessions);
    }

    @Override
    public boolean isLoggedIn(String email) {
        return getSessions().contains(email);
    }

    private List<String> getSessions() {
        List<String> sessions = new ArrayList<>();
        File file = new File(SESSION_FILE_PATH);

        if (!file.exists()) {
            return sessions;
        }

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheet("Sessions");

            if (sheet == null) {
                return sessions;
            }

            for (int i = 0; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row != null && row.getCell(0) != null) {
                    sessions.add(row.getCell(0).getStringCellValue());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading sessions from Excel: " + e.getMessage());
        }
        return sessions;
    }

    private void writeSessionsToExcel(List<String> sessions) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Sessions");

            int rowNum = 0;
            for (String email : sessions) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(email);
            }

            try (FileOutputStream fos = new FileOutputStream(SESSION_FILE_PATH)) {
                workbook.write(fos);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing sessions to Excel: " + e.getMessage());
        }
    }
}