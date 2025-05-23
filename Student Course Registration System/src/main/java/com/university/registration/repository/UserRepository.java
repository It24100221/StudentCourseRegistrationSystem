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

    // ✅ Manual Insertion Sort by Registration Time (Oldest to Newest)
    public List<User> findAllSortedByRegistrationTime() {
        List<User> users = readAll();
        return manualInsertionSort(users);
    }

    private List<User> manualInsertionSort(List<User> users) {
        // Convert to array
        User[] array = users.toArray(new User[0]);

        for (int i = 1; i < array.length; i++) {
            User key = array[i];
            int j = i - 1;

            while (j >= 0 && array[j].getRegistrationTime().isAfter(key.getRegistrationTime())) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key;
        }

        // Convert back to list
        List<User> sortedList = new ArrayList<>();
        for (User user : array) {
            sortedList.add(user);
        }

        return sortedList;
    }
}