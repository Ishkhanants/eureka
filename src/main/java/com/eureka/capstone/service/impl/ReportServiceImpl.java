package com.eureka.capstone.service.impl;

import com.eureka.capstone.domain.report.Report;
import com.eureka.capstone.exception.notfound.NotFoundException;
import com.eureka.capstone.repository.ReportRepository;
import com.eureka.capstone.service.ReportService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository repository;

    @Override
    public Report createReport(Report report) {
        return repository.save(report);
    }

    @Override
    public Report getReportById(long id) {
        return repository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public void updateReport(Report updatedReport) {
        var report = getReportById(updatedReport.getId());

        report.setComment(updatedReport.getComment());

        repository.save(report);
    }

    @Override
    public Report extractReportCommentAndIdFromRequest(HttpServletRequest request) {
        var updatedReport = new Report();

        updatedReport.setId(Long.parseLong(request.getParameter("id")));
        updatedReport.setComment(request.getParameter("edit-comment"));

        return updatedReport;
    }

    @Override
    public List<Report> getAllReports() {
        return repository.findAll();
    }

    @Override
    public void deleteReportById(long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Report> getReportsByIssueId(long id) {
        return repository.findByIssue_Id(id);
    }

}

