package com.eureka.capstone.service.issue;

import com.eureka.capstone.domain.issue.Issue;
import com.eureka.capstone.domain.issue.IssueSeverity;
import com.eureka.capstone.domain.issue.IssueStatus;
import com.eureka.capstone.domain.issue.IssueType;
import com.eureka.capstone.domain.report.Report;
import com.eureka.capstone.dto.IssueDto;
import com.eureka.capstone.exception.notfound.NotFoundException;
import com.eureka.capstone.exception.notfound.UserNotFoundException;
import com.eureka.capstone.repository.issue.IssueRepository;
import com.eureka.capstone.repository.report.ReportRepository;
import com.eureka.capstone.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

import java.net.SocketTimeoutException;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IssueServiceImpl implements IssueService {

    private final IssueRepository issueRepository;
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final JavaMailSender mailSender;

    @Override
    public Issue createIssue(Issue issue, Principal principal) {
        var user = userRepository.findByUsername(principal.getName()).orElseThrow(UserNotFoundException::new);

        if(issue.getIsConfirmationMailSent()){
            var reportForAssignee = new Report(user, issue, LocalDateTime.now(), String.format("Assigned to %s", issue.getAssignee().getFullName()));
            sendNotificationEmail(reportForAssignee);
        }

        return issueRepository.save(issue);
    }

    @Override
    public Issue getIssueById(long id) {
        return issueRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<Issue> getAllIssues() {
        return issueRepository.findAll();
    }

    @Override
    public IssueDto extractIssueDtoFromRequest(HttpServletRequest request) {
        var dto = new IssueDto();

        dto.setId(Long.parseLong(request.getParameter("id")));
        dto.setTestingDocument(request.getParameter("testing-document"));
        dto.setTitle(request.getParameter("title"));
        dto.setComment(request.getParameter("comment"));
        dto.setReportSource(request.getParameter("edit-report-source"));
        dto.setReportDate(LocalDate.parse(request.getParameter("report-date")));
        dto.setDescription(request.getParameter("description"));
        dto.setIsConfirmationMailSent(Boolean.parseBoolean(request.getParameter("send-confirmation-mail")));
        dto.setIsReportedByCustomer(Boolean.parseBoolean(request.getParameter("edit-reported-by-customer")));
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
    public void updateIssue(Issue updatedIssue, Principal principal) {
        var issue = getIssueById(updatedIssue.getId());
        var user = userRepository.findByUsername(principal.getName()).orElseThrow(UserNotFoundException::new);

        if(!issue.getAssignee().equals(updatedIssue.getAssignee())){
            var report = new Report(user, updatedIssue, LocalDateTime.now(), String.format("Assigned to %s", updatedIssue.getAssignee().getFullName()));

            if(updatedIssue.getIsConfirmationMailSent()){
                sendNotificationEmail(report);
            }

            reportRepository.save(report);
        }

        if(!issue.getStatus().equals(updatedIssue.getStatus())){
            reportRepository.save(new Report(user, issue, LocalDateTime.now(), String.format("Changed status to %s", updatedIssue.getStatus().getValueByLocale(Locale.ENGLISH))));
        }

        if(!issue.getReporter().equals(updatedIssue.getReporter())){
            reportRepository.save(new Report(user, issue, LocalDateTime.now(), String.format("Changed reporter to %s", updatedIssue.getReporter().getFullName())));
        }

        if(!issue.getProduct().equals(updatedIssue.getProduct())){
            reportRepository.save(new Report(user, issue, LocalDateTime.now(), String.format("Changed Product to %s", updatedIssue.getProduct().getName())));
        }

        if(!issue.getReleaseVersion().equals(updatedIssue.getReleaseVersion())){
            reportRepository.save(new Report(user, issue, LocalDateTime.now(), String.format("Changed release version to %s", updatedIssue.getReleaseVersion().getVersion())));
        }

        if(!issue.getSubSystem().equals(updatedIssue.getSubSystem())){
            reportRepository.save(new Report(user, issue, LocalDateTime.now(), String.format("Changed subsystem to %s", updatedIssue.getSubSystem().getName())));
        }

        if(!issue.getType().equals(updatedIssue.getType())){
            reportRepository.save(new Report(user, issue, LocalDateTime.now(), String.format("Changed issue type to %s", updatedIssue.getType().getValueByLocale(Locale.ENGLISH))));
        }

        issue.setTestingDocument(updatedIssue.getTestingDocument());
        issue.setReportDate(updatedIssue.getReportDate());
        issue.setFixDate(updatedIssue.getFixDate());
        issue.setCloseDate(updatedIssue.getCloseDate());
        issue.setDescription(updatedIssue.getDescription());
        issue.setType(updatedIssue.getType());
        issue.setTitle(updatedIssue.getTitle());
        issue.setComment(updatedIssue.getComment());
        issue.setReporter(updatedIssue.getReporter());
        issue.setAssignee(updatedIssue.getAssignee());
        issue.setSubSystem(updatedIssue.getSubSystem());
        issue.setReleaseVersion(updatedIssue.getReleaseVersion());
        issue.setIsReportedByCustomer(updatedIssue.getIsReportedByCustomer());
        issue.setIsConfirmationMailSent(updatedIssue.getIsConfirmationMailSent());
        issue.setProduct(updatedIssue.getProduct());
        issue.setStatus(updatedIssue.getStatus());
        issue.setSeverity(updatedIssue.getSeverity());
        issue.setReportSource(updatedIssue.getReportSource());

        issueRepository.save(issue);
    }

    @Override
    public void deleteIssueById(long id) {
        issueRepository.deleteById(id);
    }

    @Override
    public List<Issue> getFilteredIssues(HttpServletRequest request) {
        long productId, subsystemId, releaseVersionId, reporterId, assigneeId;
        IssueStatus status;
        IssueSeverity severity;

        var issues = getAllIssues();

        if(!request.getParameter("filter-product").isEmpty()){
            productId = Long.parseLong(request.getParameter("filter-product"));
            issues = issues.stream().filter(i -> i.getProduct().getId().equals(productId)).collect(Collectors.toList());
        }

        if(request.getParameter("filter-subsystem") != null && !request.getParameter("filter-subsystem").isEmpty()){
            subsystemId = Long.parseLong(request.getParameter("filter-subsystem"));
            issues = issues.stream().filter(i -> i.getSubSystem().getId().equals(subsystemId)).collect(Collectors.toList());
        }

        if(request.getParameter("filter-release-version") != null && !request.getParameter("filter-release-version").isEmpty()){
            releaseVersionId = Long.parseLong(request.getParameter("filter-release-version"));
            issues = issues.stream().filter(i -> i.getReleaseVersion().getId().equals(releaseVersionId)).collect(Collectors.toList());
        }

        if(!request.getParameter("filter-reporter").isEmpty()){
            reporterId = Long.parseLong(request.getParameter("filter-reporter"));
            issues = issues.stream().filter(i -> i.getReporter().getId().equals(reporterId)).collect(Collectors.toList());
        }

        if(!request.getParameter("filter-assignee").isEmpty()){
            assigneeId = Long.parseLong(request.getParameter("filter-assignee"));
            issues = issues.stream().filter(i -> i.getAssignee().getId().equals(assigneeId)).collect(Collectors.toList());
        }

        if(!request.getParameter("filter-status").isEmpty()){
            status = IssueStatus.valueOf(request.getParameter("filter-status"));
            issues = issues.stream().filter(i -> i.getStatus().equals(status)).collect(Collectors.toList());
        }

        if(!request.getParameter("filter-severity").isEmpty()){
            severity = IssueSeverity.valueOf(request.getParameter("filter-severity"));
            issues = issues.stream().filter(i -> i.getSeverity().equals(severity)).collect(Collectors.toList());
        }

        return issues;
    }

    @Retryable(value = SocketTimeoutException.class, maxAttempts = 2, backoff = @Backoff(delay = 100))
    private void sendNotificationEmail(Report report){
        var msg = new SimpleMailMessage();

        msg.setTo(report.getIssue().getAssignee().getEmail());
        msg.setSubject(String.format("New Assignment from Issue with Title: %s", report.getIssue().getTitle()));
        msg.setText(String.format("Dear %s,\n\nIssue with title \"%s\" has been assigned to you.\n\nRespectfully,\nEureka Development Team", report.getIssue().getAssignee().getFullName(), report.getIssue().getTitle()));

        mailSender.send(msg);
    }

}
