package com.eureka.capstone.service;

import com.eureka.capstone.domain.issue.Issue;
import com.eureka.capstone.dto.IssueDto;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

public interface IssueService {

    Issue createIssue(Issue issue, Principal principal);

    Issue getIssueById(long id);

    List<Issue> getAllIssues();

    IssueDto extractIssueDtoFromRequest(HttpServletRequest request);

    void updateIssue(Issue issue, Principal principal);

    void deleteIssueById(long id);

}
