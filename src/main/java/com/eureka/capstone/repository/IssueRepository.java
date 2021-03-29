package com.eureka.capstone.repository;

import com.eureka.capstone.domain.issue.Issue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {}
