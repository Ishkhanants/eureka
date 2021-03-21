package com.eureka.capstone.domain.report;

import com.eureka.capstone.domain.BaseEntity;
import com.eureka.capstone.domain.issue.Issue;
import com.eureka.capstone.domain.product.Product;
import com.eureka.capstone.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "REPORT")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Report extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User reporter;

    @ManyToOne
    @JoinColumn(name = "issue_id", nullable = false)
    private Issue issue;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @Column(name = "comment", nullable = false)
    private String comment;

}
