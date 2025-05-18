package com.university.registration.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.university.registration.model.User;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository extends FileRepository<User> {

    public UserRepository() {
        super("users.txt");
    }

    @PostConstruct
    public void init() {
        List<User> initialUsers = new ArrayList<>();
        initialUsers.add(new User("admin@gmail.com", "Admin User", "admin"));
        saveAll(initialUsers);
    }

    @Override
    protected TypeReference<List<User>> getTypeReference() {
        return new TypeReference<>() {};
    }

    public List<User> findAll() {
        return readAll();
    }

    public Optional<User> findByEmail(String email) {
        return readAll().stream().filter(user -> user.getEmail().equals(email)).findFirst();
    }

    public User save(User user) {
        List<User> users = readAll();
        users.removeIf(u -> u.getEmail().equals(user.getEmail()));
        users.add(user);
        saveAll(users);
        return user;
    }

    public void deleteByEmail(String email) {
        List<User> users = readAll();
        users.removeIf(user -> user.getEmail().equals(email));
        saveAll(users);
    }

    // Insertion Sort by Registration Time
    public List<User> findAllSortedByRegistrationTime() {
        List<User> users = readAll();
        return insertionSortByRegistrationTime(users);
    }

    private List<User> insertionSortByRegistrationTime(List<User> users) {
        List<User> sortedUsers = new ArrayList<>(users);
        for (int i = 1; i < sortedUsers.size(); i++) {
            User key = sortedUsers.get(i);
            int j = i - 1;
            while (j >= 0 && sortedUsers.get(j).getRegistrationTime().isAfter(key.getRegistrationTime())) {
                sortedUsers.set(j + 1, sortedUsers.get(j));
                j--;
            }
            sortedUsers.set(j + 1, key);
        }
        return sortedUsers;
    }
}