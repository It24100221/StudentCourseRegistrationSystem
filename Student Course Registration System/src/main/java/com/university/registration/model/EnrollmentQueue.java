package com.university.registration.model;

public class EnrollmentQueue {
    private static final int MAX_SIZE = 10;
    private Enrollment[] queue;
    private int size;

    public EnrollmentQueue() {
        this.queue = new Enrollment[MAX_SIZE];
        this.size = 0;
    }

    // Enqueue + Insertion sort by timestamp
    public boolean enqueue(Enrollment enrollment) {
        if (size >= MAX_SIZE) {
            return false; // Queue is full, don't remove existing enrollments
        }

        queue[size] = enrollment;
        size++;
        insertionSortByTimestamp();
        return true;
    }

    // Insertion sort ascending order by timestamp
    private void insertionSortByTimestamp() {
        for (int i = 1; i < size; i++) {
            Enrollment key = queue[i];
            int j = i - 1;

            while (j >= 0 && queue[j].getTimestamp() > key.getTimestamp()) {
                queue[j + 1] = queue[j];
                j--;
            }
            queue[j + 1] = key;
        }
    }



    // Get current enrollments (sorted)
    public Enrollment[] getAll() {
        Enrollment[] result = new Enrollment[size];
        for (int i = 0; i < size; i++) {
            result[i] = queue[i];
        }
        return result;
    }

    public int size() {
        return size;
    }

    public void clear() {
        for (int i = 0; i < MAX_SIZE; i++) {
            queue[i] = null;
        }
        size = 0;
    }
}
