package com.nayana.demo.repository;


import com.nayana.demo.model.Support;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SupportRepository {
    private static final String FILE_PATH = "support.txt";

    public void save(Support support) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(support.getId() + "," + support.getStudentName() + "," + support.getType() + "," + support.getMessage());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Support> findAll() {
        List<Support> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Support support = new Support(Integer.parseInt(parts[0]), parts[1], parts[2], parts[3]);
                list.add(support);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
    //


}
