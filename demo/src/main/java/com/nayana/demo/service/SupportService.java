package com.nayana.demo.service;

import com.nayana.demo.model.Support;
import com.nayana.demo.repository.SupportRepository;

import java.util.List;

public class SupportService {
    private SupportRepository repository = new SupportRepository();

    public void addSupport(Support support) {
        repository.save(support);
    }

    public List<Support> getAllSupportRequests() {
        return repository.findAll();
    }
}
