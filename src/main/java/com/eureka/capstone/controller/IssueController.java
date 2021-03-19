package com.eureka.capstone.controller;

import com.eureka.capstone.domain.issue.Issue;
import com.eureka.capstone.domain.report.Report;
import com.eureka.capstone.dto.IssueDto;
import com.eureka.capstone.mapping.issue.IssueMapper;
import com.eureka.capstone.service.IssueService;
import com.eureka.capstone.service.ReportService;
import com.eureka.capstone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/issues")
@RequiredArgsConstructor
public class IssueController {

    private final UserService userService;
    private final ReportService reportService;
    private final IssueService issueService;
    private final IssueMapper issueMapper;

//    @GetMapping("/myIssues")
//    public String myIssues(Model model, Principal principal) {
//        return Templates.ISSUES.getName();
//    }

    @GetMapping
    public ModelAndView success(ModelAndView modelAndView) {
        List<Issue> listIssues = issueService.getAllIssues();
        modelAndView.setViewName(Templates.ISSUES.getName());
        modelAndView.addObject("listIssues", listIssues);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView getAddIssuePage() {
        ModelAndView modelAndView = new ModelAndView(Templates.ADD_ISSUE.getName());
        modelAndView.addObject("issue", new IssueDto());
        return modelAndView;
    }

    @PostMapping("/create")
    public String addIssue(@ModelAttribute("issue") IssueDto issueDto){
        var entity = issueMapper.toEntity(issueDto);
        issueService.createIssue(entity);
        return "redirect:/issues";
    }

    @GetMapping("/edit/{id}")
    public ModelAndView getEditIssuePage(@PathVariable("id") long id) {
        ModelAndView modelAndView = new ModelAndView(Templates.EDIT_ISSUE.getName());
        var dto = issueMapper.toDto(issueService.getIssueById(id));
        modelAndView.addObject("issue", dto);
        return modelAndView;
    }

    @PostMapping("/edit")
    public String updateIssue(HttpServletRequest request) {
        var issueDto = issueService.extractIssueDtoFromRequest(request);
        var issueEntity = issueMapper.toEntity(issueDto);
        issueService.updateIssue(issueEntity);
        return "redirect:/issues";
    }

    @PostMapping("/delete")
    public String deleteIssue(HttpServletRequest request){
        long id = Long.parseLong(request.getParameter("id"));
        issueService.deleteIssueById(id);
        return "redirect:/issues";
    }

    @PostMapping("/delete-selected")
    public String deleteSelectedIssues(HttpServletRequest request) {
        var ids = request.getParameter("ids").split(",");

        for (String id: ids) {
            long idl = Long.parseLong(id);
            issueService.deleteIssueById(idl);
        }

        return "redirect:/issues";
    }

    @GetMapping("/reports")
    public ModelAndView getReportsPage(ModelAndView modelAndView) {
        List<Report> listReports = reportService.getAllReports();
        modelAndView.setViewName(Templates.REPORTS.getName());
        modelAndView.addObject("listReports", listReports);
        return modelAndView;
    }

    @GetMapping("/{id}/reports")
    public ModelAndView getReports(@PathVariable("id") long id, ModelAndView modelAndView){
        var reports = reportService.getReportsByIssueId(id);
        var report = new Report();
        report.setIssue(issueService.getIssueById(id));
        modelAndView.setViewName(Templates.REPORTS.getName());
        modelAndView.addObject("listReports", reports);
        modelAndView.addObject("report", report);
        return modelAndView;
    }

    @PostMapping("/reports/create")
    public String addReport(@ModelAttribute("report") Report report, HttpServletRequest request, Principal principal){
        var issueId = Long.parseLong(request.getParameter("issue-id-to-add"));
        report.setIssue(issueService.getIssueById(issueId));
        report.setDateTime(LocalDateTime.now());
        report.setReporter(userService.getUserByUsername(principal.getName()));
        reportService.createReport(report);
        return "redirect:/issues/" + issueId + "/reports";
    }

    @GetMapping(value = "/reports/{id}", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<Report> getReportById(@PathVariable("id") long id) {
        try {
            return new ResponseEntity<>(reportService.getReportById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/reports-by-issue/{id}", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Report>> getReportsByIssueId(@PathVariable("id") long id){
        try {
            return new ResponseEntity<>(reportService.getReportsByIssueId(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/reports/edit")
    public String editReport(HttpServletRequest request){
        var issueId = Long.parseLong(request.getParameter("issue-id-to-edit"));
        var updatedReport = reportService.extractReportCommentAndIdFromRequest(request);
        updatedReport.setIssue(issueService.getIssueById(issueId));
        reportService.updateReport(updatedReport);
        return "redirect:/issues/" + issueId + "/reports";
    }

    @PostMapping("/reports/delete")
    public String deleteReport(HttpServletRequest request){
        var issueId = Long.parseLong(request.getParameter("issue-id-to-delete"));
        var id = Long.parseLong(request.getParameter("id"));
        reportService.deleteReportById(id);
        return "redirect:/issues/" + issueId + "/reports";
    }

    @PostMapping("/reports/delete-selected")
    public String deleteSelectedReports(HttpServletRequest request) {
        var issueId = Long.parseLong(request.getParameter("issue-ids-to-delete"));
        var ids = request.getParameter("ids").split(",");

        for (String id: ids) {
            long idl = Long.parseLong(id);
            reportService.deleteReportById(idl);
        }

        return "redirect:/issues/" + issueId + "/reports";
    }
}
