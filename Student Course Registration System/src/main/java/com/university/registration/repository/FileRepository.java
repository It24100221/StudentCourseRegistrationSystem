
        package com.university.registration.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class FileRepository<T> {

    private final String filePath;
    private final ObjectMapper objectMapper;
    private final boolean useLineByLineFormat;

    public FileRepository(String filePath) {
        this(filePath, false);
    }

    public FileRepository(String filePath, boolean useLineByLineFormat) {
        this.filePath = new File(filePath).getAbsolutePath();
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule()); // Support for LocalDateTime
        this.useLineByLineFormat = useLineByLineFormat;
        System.out.println("Initialized file repository with path: " + this.filePath + ", line-by-line: " + useLineByLineFormat);
    }

    protected abstract TypeReference<List<T>> getTypeReference();

    protected synchronized List<T> readAll() {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                return new ArrayList<>();
            }
            if (useLineByLineFormat) {
                return readLineByLine(file);
            } else {
                return objectMapper.readValue(file, getTypeReference());
            }
        } catch (IOException e) {
            System.err.println("Error reading from file: " + filePath + " - " + e.getMessage());
            throw new RuntimeException("Failed to read from file: " + filePath, e);
        }
    }

    protected synchronized void saveAll(List<T> entities) {
        try {
            File file = new File(filePath);
            if (useLineByLineFormat) {
                writeLineByLine(file, entities);
            } else {
                objectMapper.writeValue(file, entities);
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + filePath + " - " + e.getMessage());
            throw new RuntimeException("Error writing to file: " + filePath, e);
        }
    }

    private List<T> readLineByLine(File file) throws IOException {
        List<T> entities = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    T entity = objectMapper.readValue(line, objectMapper.getTypeFactory().constructType(getTypeReference().getType()));
                    entities.add(entity);
                }
            }
        }
        return entities;
    }

    private void writeLineByLine(File file, List<T> entities) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (T entity : entities) {
                writer.write(objectMapper.writeValueAsString(entity));
                writer.newLine();
            }
        }
    }
}
