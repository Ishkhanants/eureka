package com.eureka.capstone.service;

import com.eureka.capstone.domain.report.Report;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

public interface ReportService {

    Report createReport(Report report);

    Report getReportById(long id);

    void updateReport(Report report);

    Report extractReportCommentAndIdFromRequest(HttpServletRequest request);

    List<Report> getAllReports();

    void deleteReportById(long id);

    List<Report> getReportsByIssueId(long id);

}
