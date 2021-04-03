package com.eureka.capstone.dto;

import com.eureka.capstone.domain.issue.IssueSeverity;
import com.eureka.capstone.domain.issue.IssueStatus;
import com.eureka.capstone.domain.issue.IssueType;

import lombok.*;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IssueDto {
    private long id;

    private long productId;

    private long subSystemId;

    private long releaseVersionId;

    private IssueStatus status;

    private IssueSeverity severity;

    private IssueType type;

    private long reporterId;

    private long assigneeId;

    private String title;

    private String description;

    private String reportSource;

    private String testingDocument;

    private String comment;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate reportDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fixDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate closeDate;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private boolean isConfirmationMailSent;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private boolean isReportedByCustomer;

    public boolean getIsReportedByCustomer() {
        return isReportedByCustomer;
    }

    public void setIsReportedByCustomer(boolean reportedByCustomer) {
        isReportedByCustomer = reportedByCustomer;
    }

    public boolean getIsConfirmationMailSent() {
        return isConfirmationMailSent;
    }

    public void setIsConfirmationMailSent(boolean confirmationMailSent) {
        isConfirmationMailSent = confirmationMailSent;
    }

}
