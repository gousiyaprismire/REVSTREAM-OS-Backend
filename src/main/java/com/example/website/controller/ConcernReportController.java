package com.example.website.controller;

import com.example.website.dto.ConcernReportRequest;
import com.example.website.entity.ConcernReport;
import com.example.website.service.ConcernReportService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/concerns")
@CrossOrigin("*")
public class ConcernReportController {

    private final ConcernReportService service;

    public ConcernReportController(ConcernReportService service) {
        this.service = service;
    }

    // Submit report
    @PostMapping
    public ResponseEntity<ConcernReport> createReport(
            @RequestBody ConcernReportRequest request
    ) {
        return ResponseEntity.ok(service.createReport(request));
    }

    // Admin - list all reports
    @GetMapping
    public ResponseEntity<List<ConcernReport>> getAllReports() {
        return ResponseEntity.ok(service.getAllReports());
    }

    // Admin - get single report
    @GetMapping("/{id}")
    public ResponseEntity<ConcernReport> getReport(@PathVariable Long id) {
        return ResponseEntity.ok(service.getReportById(id));
    }
}
