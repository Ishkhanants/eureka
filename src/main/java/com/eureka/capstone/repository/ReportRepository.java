package com.eureka.capstone.repository;

import com.eureka.capstone.domain.report.Report;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    List<Report> findByIssue_Id(Long issueId);

}
