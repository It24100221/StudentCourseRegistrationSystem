package com.university.registration.controller;

import com.university.registration.model.Support;
import com.university.registration.service.SupportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/support")
public class SupportController {

    private final SupportService service;

    @Autowired
    public SupportController(SupportService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addSupport(@RequestBody Support support) {
        try {
            service.addSupport(support);
            return ResponseEntity.ok("Support request added.");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error adding support request: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Support>> getAll() {
        try {
            List<Support> supports = service.getAllSupportRequests();
            return ResponseEntity.ok(supports);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}