package com.university.registration.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;

import org.springframework.beans.factory.annotation.Value;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public abstract class AbstractFileService<T> {

    protected final String filePath;
    protected final ObjectMapper objectMapper;

    public AbstractFileService(@Value("${file.path}") String filePath) {
        this.filePath = filePath;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    protected List<T> readFromFile() throws IOException {
        List<T> entities = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            return entities;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    T entity = objectMapper.readValue(line, getEntityClass());
                    entities.add(entity);
                }
            }
        }
        return entities;
    }

    protected void writeToFile(List<T> entities) throws IOException {
        // Ensure directory exists
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            boolean dirCreated = parentDir.mkdirs();
            if (!dirCreated) {
                throw new IOException("Failed to create directory: " + parentDir.getAbsolutePath());
            }
        }
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (T entity : entities) {
                writer.write(objectMapper.writeValueAsString(entity));
                writer.newLine();
            }
        }
    }

    protected abstract Class<T> getEntityClass();
}