package com.university.registration.service;

import com.university.registration.model.Feedback;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class FeedbackService extends AbstractFileService<Feedback> {

    private final AtomicLong idGenerator = new AtomicLong(1);

    public FeedbackService() {
        super("src/main/resources/feedback.txt");
        try {
            List<Feedback> feedbacks = readFromFile();
            if (!feedbacks.isEmpty()) {
                long maxId = feedbacks.stream().mapToLong(Feedback::getId).max().orElse(0);
                idGenerator.set(maxId + 1);
            }
        } catch (IOException e) {
            System.err.println("Error initializing ID generator: " + e.getMessage());
        }
    }

    @Override
    protected Class<Feedback> getEntityClass() {
        return Feedback.class;
    }

    public List<Feedback> getAllFeedbacks() {
        try {
            return readFromFile();
        } catch (IOException e) {
            throw new RuntimeException("Error retrieving feedbacks", e);
        }
    }

    public Feedback getFeedbackById(Long id) {
        try {
            return readFromFile().stream()
                    .filter(f -> f.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Feedback not found with ID: " + id));
        } catch (IOException e) {
            throw new RuntimeException("Error retrieving feedback with ID: " + id, e);
        }
    }

    public Feedback saveFeedback(Feedback feedback) {
        try {
            List<Feedback> feedbacks = readFromFile();
            feedback.setUpdatedAt(LocalDateTime.now());
    
            if (feedback.getId() == null) {
                // Set a new ID if none exists
                feedback.setId(idGenerator.getAndIncrement());
                feedback.setCreatedAt(LocalDateTime.now());
                feedbacks.add(feedback);
            } else {
                // Update existing feedback
                boolean updated = false;
                for (int i = 0; i < feedbacks.size(); i++) {
                    if (feedbacks.get(i).getId().equals(feedback.getId())) {
                        // Preserve creation date from original record
                        feedback.setCreatedAt(feedbacks.get(i).getCreatedAt());
                        feedbacks.set(i, feedback);
                        updated = true;
                        break;
                    }
                }
                if (!updated) {
                    // If no existing feedback found with this ID, treat as new
                    feedback.setCreatedAt(LocalDateTime.now());
                    feedbacks.add(feedback);
                }
            }
            
            writeToFile(feedbacks);
            return feedback;
        } catch (IOException e) {
            throw new RuntimeException("Error saving feedback: " + e.getMessage(), e);
        }
    }

    public void deleteFeedback(Long id) {
        try {
            List<Feedback> feedbacks = readFromFile();
            boolean removed = feedbacks.removeIf(f -> f.getId().equals(id));
            if (!removed) {
                throw new IllegalArgumentException("Feedback not found with ID: " + id);
            }
            writeToFile(feedbacks);
        } catch (IOException e) {
            throw new RuntimeException("Error deleting feedback with ID: " + id, e);
        }
    }
}