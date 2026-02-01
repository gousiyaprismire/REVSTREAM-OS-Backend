package com.example.website.service;

import com.example.website.dto.ConcernReportRequest;
import com.example.website.entity.ConcernReport;

import java.util.List;

public interface ConcernReportService {

    ConcernReport createReport(ConcernReportRequest request);

    List<ConcernReport> getAllReports();

    ConcernReport getReportById(Long id);
}
