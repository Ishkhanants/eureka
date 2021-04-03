package com.eureka.capstone.domain.issue;

import com.eureka.capstone.domain.BaseEntity;
import com.eureka.capstone.domain.product.Product;
import com.eureka.capstone.domain.product.ReleaseVersion;
import com.eureka.capstone.domain.product.SubSystem;
import com.eureka.capstone.domain.user.User;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.*;

import org.hibernate.annotations.Type;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ISSUE")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Issue extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "subsystem_id", nullable = false)
    private SubSystem subSystem;

    @ManyToOne
    @JoinColumn(name = "release_version_id", nullable = false)
    private ReleaseVersion releaseVersion;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private IssueStatus status;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "severity")
    private IssueSeverity severity;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "type", nullable = false)
    private IssueType type;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Column(name = "reported_by_customer")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean isReportedByCustomer;

    @ManyToOne
    @JoinColumn(name = "reported_by", nullable = false)
    private User reporter;

    @ManyToOne
    @JoinColumn(name = "assigned_to", nullable = false)
    private User assignee;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "comment")
    private String comment;

    @Column(name = "report_source")
    private String reportSource;

    @Column(name = "testing_document")
    private String testingDocument;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Column(name = "confirmation_mail_sent")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean isConfirmationMailSent;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "report_date", nullable = false)
    private LocalDate reportDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fix_date")
    private LocalDate fixDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "close_date")
    private LocalDate closeDate;

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
