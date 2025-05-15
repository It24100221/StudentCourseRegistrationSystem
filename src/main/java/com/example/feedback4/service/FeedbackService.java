package com.example.feedback4.service;

import com.example.feedback4.model.Feedback;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class FeedbackService {
    private static final String FILE_PATH = "src/main/resources/feedback.txt";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public FeedbackService() {
        objectMapper.registerModule(new JavaTimeModule());
        try {
            List<Feedback> feedbacks = readFeedbacksFromFile();
            if (!feedbacks.isEmpty()) {
                long maxId = feedbacks.stream().mapToLong(Feedback::getId).max().orElse(0);
                idGenerator.set(maxId + 1);
            }
        } catch (IOException e) {
            System.err.println("Error initializing ID generator: " + e.getMessage());
        }
    }

    public List<Feedback> getAllFeedbacks() throws IOException {
        return readFeedbacksFromFile();
    }

    public Feedback getFeedbackById(Long id) throws IOException {
        return readFeedbacksFromFile().stream()
                .filter(f -> f.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Feedback not found with ID: " + id));
    }

    public Feedback saveFeedback(Feedback feedback) throws IOException {
        List<Feedback> feedbacks = readFeedbacksFromFile();
        feedback.setUpdatedAt(LocalDateTime.now());

        if (feedback.getId() == null) {
            feedback.setId(idGenerator.getAndIncrement());
            feedbacks.add(feedback);
        } else {
            feedbacks.removeIf(f -> f.getId().equals(feedback.getId()));
            feedbacks.add(feedback);
        }

        writeFeedbacksToFile(feedbacks);
        return feedback;
    }

    public void deleteFeedback(Long id) throws IOException {
        List<Feedback> feedbacks = readFeedbacksFromFile();
        boolean removed = feedbacks.removeIf(f -> f.getId().equals(id));
        if (!removed) {
            throw new IllegalArgumentException("Feedback not found with ID: " + id);
        }
        writeFeedbacksToFile(feedbacks);
    }

    private List<Feedback> readFeedbacksFromFile() throws IOException {
        List<Feedback> feedbacks = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return feedbacks;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    Feedback feedback = objectMapper.readValue(line, Feedback.class);
                    feedbacks.add(feedback);
                }
            }
        }
        return feedbacks;
    }

    private void writeFeedbacksToFile(List<Feedback> feedbacks) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Feedback feedback : feedbacks) {
                writer.write(objectMapper.writeValueAsString(feedback));
                writer.newLine();
            }
        }
    }
}