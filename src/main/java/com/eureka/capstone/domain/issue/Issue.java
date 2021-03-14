package com.eureka.capstone.domain.issue;

import com.eureka.capstone.domain.BaseEntity;
import com.eureka.capstone.domain.product.Product;
import com.eureka.capstone.domain.product.SubSystem;
import com.eureka.capstone.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Entity
@NoArgsConstructor
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

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private IssueStatus status;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "severity")
    private IssueSeverity severity;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "type")
    private IssueType type;

    @Column(name = "added_to_release_note")
    private boolean isAddedToReleaseNote;

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

    @Column(name = "comment", nullable = false)
    private String comment;

    @Column(name = "confirmation_mail_sent")
    private boolean isConfirmationMailSent;
}
