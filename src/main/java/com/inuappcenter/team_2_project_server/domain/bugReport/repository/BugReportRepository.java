package com.inuappcenter.team_2_project_server.domain.bugReport.repository;

import com.inuappcenter.team_2_project_server.domain.bugReport.entity.BugReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BugReportRepository extends JpaRepository<BugReport, Long> {
}
