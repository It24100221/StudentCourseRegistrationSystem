package com.university.registration.service;

import com.university.registration.exception.ResourceNotFoundException;
import com.university.registration.model.Student;
import com.university.registration.model.User;
import com.university.registration.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsersSortedByRegistrationTime() {
        return userRepository.findAllSortedByRegistrationTime();
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
    }

    public User createUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already registered");
        }
        return userRepository.save(user);
    }

    public User updateUser(String email, User user) {
        if (!userRepository.findByEmail(email).isPresent()) {
            throw new ResourceNotFoundException("User not found with email: " + email);
        }
        user.setEmail(email);
        return userRepository.save(user);
    }

    public void deleteUser(String email) {
        if (!userRepository.findByEmail(email).isPresent()) {
            throw new ResourceNotFoundException("User not found with email: " + email);
        }
        userRepository.deleteByEmail(email);
    }

    public User login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid email or password");
        }
        return user;
    }

    /**
     * Converts a User to a Student object for enrollment purposes.
     * 
     * @param user The user to convert
     * @return A new Student object with data from the user
     */
    public Student convertToStudent(User user) {
        // Generate a unique student ID using UUID
        String studentId = "STU-" + UUID.randomUUID().toString().substring(0, 8);

        // Create a new Student object with data from the User
        return new Student(studentId, user.getFullName(), user.getEmail());
    }
}
