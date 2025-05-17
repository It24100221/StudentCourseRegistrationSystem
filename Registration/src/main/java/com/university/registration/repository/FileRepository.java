package com.university.registration.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class FileRepository<T> {

    private final String filePath;
    private final ObjectMapper objectMapper;

    public FileRepository(String filePath) {
        this.filePath = filePath;
        this.objectMapper = new ObjectMapper();
    }

    protected abstract TypeReference<List<T>> getTypeReference();

    protected List<T> readAll() {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                return new ArrayList<>();
            }
            return objectMapper.readValue(file, getTypeReference());
        } catch (IOException e) {
            throw new RuntimeException("Error reading from file: " + filePath, e);
        }
    }

    protected void saveAll(List<T> entities) {
        try {
            objectMapper.writeValue(new File(filePath), entities);
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file: " + filePath, e);
        }
    }
}