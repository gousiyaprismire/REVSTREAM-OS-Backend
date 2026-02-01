package com.example.website.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.website.entity.ConcernReport;

public interface ConcernReportRepository extends JpaRepository<ConcernReport, Long> {
}
