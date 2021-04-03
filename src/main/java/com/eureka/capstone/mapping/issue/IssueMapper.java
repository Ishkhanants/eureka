package com.eureka.capstone.mapping.issue;

import com.eureka.capstone.domain.issue.Issue;
import com.eureka.capstone.dto.IssueDto;

import com.eureka.capstone.service.product.ProductService;
import com.eureka.capstone.service.product.ReleaseVersionService;
import com.eureka.capstone.service.product.SubsystemService;
import com.eureka.capstone.service.user.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IssueMapper {

    private final ProductService productService;
    private final UserService userService;
    private final SubsystemService subsystemService;
    private final ReleaseVersionService releaseVersionService;

    public IssueDto toDto(Issue entity){
        var dto = new IssueDto();

        dto.setId(entity.getId());
        dto.setIsConfirmationMailSent(entity.getIsConfirmationMailSent());
        dto.setIsFoundInTheField(entity.getIsFoundInTheField());
        dto.setProductId(entity.getProduct().getId());
        dto.setAssigneeId(entity.getAssignee().getId());
        dto.setReporterId(entity.getReporter().getId());
        dto.setDescription(entity.getDescription());
        dto.setStatus(entity.getStatus());
        dto.setReportDate(entity.getReportDate());
        dto.setFixDate(entity.getFixDate());
        dto.setCloseDate(entity.getCloseDate());
        dto.setReportSource(entity.getReportSource());
        dto.setTitle(entity.getTitle());
        dto.setSeverity(entity.getSeverity());
        dto.setType(entity.getType());
        dto.setSubSystemId(entity.getSubSystem().getId());
        dto.setReleaseVersionId(entity.getReleaseVersion().getId());
        dto.setTestingDocument(entity.getTestingDocument());
        dto.setComment(entity.getComment());

        return dto;
    }

    public Issue toEntity(IssueDto dto){
        var entity = new Issue();

        entity.setId(dto.getId());
        entity.setIsConfirmationMailSent(dto.getIsConfirmationMailSent());
        entity.setIsFoundInTheField(dto.getIsFoundInTheField());
        entity.setProduct(productService.getProductById(dto.getProductId()));
        entity.setAssignee(userService.getUserById(dto.getAssigneeId()));
        entity.setReporter(userService.getUserById(dto.getReporterId()));
        entity.setSubSystem(subsystemService.getSubSystemById(dto.getSubSystemId()));
        entity.setReleaseVersion(releaseVersionService.getReleaseVersionById(dto.getReleaseVersionId()));
        entity.setStatus(dto.getStatus());
        entity.setTitle(dto.getTitle());
        entity.setComment(dto.getComment());
        entity.setType(dto.getType());
        entity.setSeverity(dto.getSeverity());
        entity.setDescription(dto.getDescription());
        entity.setReportSource(dto.getReportSource());
        entity.setReportDate(dto.getReportDate());
        entity.setFixDate(dto.getFixDate());
        entity.setCloseDate(dto.getCloseDate());
        entity.setTestingDocument(dto.getTestingDocument());

        return entity;
    }

}
