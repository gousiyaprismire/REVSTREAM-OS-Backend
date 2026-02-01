package com.example.website.service;

import com.example.website.dto.ConcernReportRequest;
import com.example.website.entity.ConcernReport;
import com.example.website.repository.ConcernReportRepository;
import com.example.website.service.ConcernReportService;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConcernReportServiceImpl implements ConcernReportService {

    private final ConcernReportRepository repository;

    public ConcernReportServiceImpl(ConcernReportRepository repository) {
        this.repository = repository;
    }

    @Override
    public ConcernReport createReport(ConcernReportRequest request) {

        ConcernReport report = new ConcernReport();
        report.setCategory(request.getCategory());
        report.setSeverity(request.getSeverity());
        report.setAffectedAreas(request.getAffectedAreas());
        report.setDescription(request.getDescription());
        report.setIncidentDate(request.getIncidentDate());
        report.setIncidentTime(request.getIncidentTime());
        report.setContactEmail(request.getContactEmail());

        return repository.save(report);
    }

    @Override
    public List<ConcernReport> getAllReports() {
        return repository.findAll();
    }

    @Override
    public ConcernReport getReportById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Report not found"));
    }
}
