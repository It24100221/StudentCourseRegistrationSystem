package com.university.registration.service;

import com.university.registration.model.Support;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class SupportService extends AbstractFileService<Support> {

    private final AtomicInteger idGenerator = new AtomicInteger(1);

    public SupportService() {
        super("src/main/resources/support.txt");
        try {
            List<Support> supports = readFromFile();
            if (!supports.isEmpty()) {
                int maxId = supports.stream().mapToInt(Support::getId).max().orElse(0);
                idGenerator.set(maxId + 1);
            }
        } catch (IOException e) {
            System.err.println("Error initializing ID generator: " + e.getMessage());
        }
    }

    @Override
    protected Class<Support> getEntityClass() {
        return Support.class;
    }

    public void addSupport(Support support) throws IOException {
        List<Support> supports = readFromFile();
        if (support.getId() == 0) {
            support.setId(idGenerator.getAndIncrement());
        }
        supports.add(support);
        writeToFile(supports);
    }

    public List<Support> getAllSupportRequests() throws IOException {
        return readFromFile();
    }
}