package com.nayana.demo.controller;


import com.nayana.demo.model.Support;
import com.nayana.demo.service.SupportService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/support")
public class SupportController {
    private SupportService service = new SupportService();
    private AtomicInteger idGenerator = new AtomicInteger(1);

    @PostMapping("/add")
    public String addSupport(@RequestBody Support support) {
        support.setId(idGenerator.getAndIncrement());
        service.addSupport(support);
        return "Support request added.";
    }

    @GetMapping("/all")
    public List<Support> getAll() {
        return service.getAllSupportRequests();
    }
}
