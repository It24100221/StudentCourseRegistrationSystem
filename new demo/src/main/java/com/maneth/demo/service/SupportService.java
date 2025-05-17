package com.maneth.demo.service;

import com.maneth.demo.repository.SupportRepository;
import com.maneth.demo.model.Support;

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
