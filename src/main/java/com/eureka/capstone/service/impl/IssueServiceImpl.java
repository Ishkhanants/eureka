package com.eureka.capstone.service.impl;

import com.eureka.capstone.domain.issue.Issue;
import com.eureka.capstone.domain.issue.IssueSeverity;
import com.eureka.capstone.domain.issue.IssueStatus;
import com.eureka.capstone.domain.issue.IssueType;
import com.eureka.capstone.dto.IssueDto;
import com.eureka.capstone.exception.notfound.NotFoundException;
import com.eureka.capstone.repository.IssueRepository;
import com.eureka.capstone.service.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IssueServiceImpl implements IssueService {

    private final IssueRepository repository;

    @Override
    public Issue createIssue(Issue issue) {
        return repository.save(issue);
    }

    @Override
    public Issue getIssueById(long id) {
        return repository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<Issue> getAllIssues() {
        return repository.findAll();
    }

    @Override
    public IssueDto extractIssueDtoFromRequest(HttpServletRequest request) {
        var dto = new IssueDto();

        dto.setId(Long.parseLong(request.getParameter("id")));
        dto.setTestingDocument(request.getParameter("testing-document"));
        dto.setTitle(request.getParameter("title"));
        dto.setComment(request.getParameter("comment"));
        dto.setReportSource(request.getParameter("report-source"));
        dto.setReportDate(LocalDate.parse(request.getParameter("report-date")));
        dto.setDescription(request.getParameter("description"));
        dto.setIsConfirmationMailSent(Boolean.parseBoolean(request.getParameter("send-confirmation-mail")));
        dto.setIsFoundInTheField(Boolean.parseBoolean(request.getParameter("found-in-the-field")));
        dto.setProductId(Long.parseLong(request.getParameter("product")));
        dto.setSubSystemId(Long.parseLong(request.getParameter("subsystem")));
        dto.setReleaseVersionId(Long.parseLong(request.getParameter("release-version")));
        dto.setReporterId(Long.parseLong(request.getParameter("reporter")));
        dto.setAssigneeId(Long.parseLong(request.getParameter("assignee")));

        if(!request.getParameter("fix-date").isEmpty()){
            dto.setFixDate(LocalDate.parse(request.getParameter("fix-date")));
        }

        if (!request.getParameter("close-date").isEmpty()) {
            dto.setCloseDate(LocalDate.parse(request.getParameter("close-date")));
        }

        if(!request.getParameter("status").isEmpty()){
            dto.setStatus(IssueStatus.valueOf(request.getParameter("status")));
        }

        if(!request.getParameter("severity").isEmpty()){
            dto.setSeverity(IssueSeverity.valueOf(request.getParameter("severity")));
        }

        if(!request.getParameter("type").isEmpty()){
            dto.setType(IssueType.valueOf(request.getParameter("type")));
        }

        return dto;
    }

    @Override
    public void updateIssue(Issue updatedIssue) {
        var issue = getIssueById(updatedIssue.getId());
        issue.setTestingDocument(updatedIssue.getTestingDocument());
        issue.setReportDate(updatedIssue.getReportDate());
        issue.setDescription(updatedIssue.getDescription());
        issue.setType(updatedIssue.getType());
        issue.setTitle(updatedIssue.getTitle());
        issue.setReporter(updatedIssue.getReporter());
        issue.setAssignee(updatedIssue.getAssignee());
        issue.setSubSystem(updatedIssue.getSubSystem());
        issue.setReleaseVersion(updatedIssue.getReleaseVersion());
        issue.setIsFoundInTheField(updatedIssue.getIsFoundInTheField());
        issue.setIsConfirmationMailSent(updatedIssue.getIsConfirmationMailSent());
        issue.setProduct(updatedIssue.getProduct());
        issue.setStatus(updatedIssue.getStatus());
        issue.setReportSource(updatedIssue.getReportSource());
        repository.save(issue);
    }

    @Override
    public void deleteIssueById(long id) {
        repository.deleteById(id);
    }

}
