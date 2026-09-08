package com.inuappcenter.team_2_project_server.domain.bugReport.service;

import com.inuappcenter.team_2_project_server.domain.bugReport.dto.BugReportRequestDto;
import com.inuappcenter.team_2_project_server.domain.bugReport.dto.BugReportResponseDto;
import com.inuappcenter.team_2_project_server.domain.bugReport.entity.BugReport;
import com.inuappcenter.team_2_project_server.domain.bugReport.repository.BugReportRepository;
import com.inuappcenter.team_2_project_server.global.error.ex.ErrorCode;
import com.inuappcenter.team_2_project_server.global.error.ex.MyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class BugReportService {

    private final BugReportRepository bugReportRepository;

    /**
     * 오류제보 단건 조회
     */
    public BugReportResponseDto getBugReport(Long bugReportId) {
        BugReport bugReport = bugReportRepository.findById(bugReportId)
                .orElseThrow(() -> new MyException(ErrorCode.BUG_REPORT_NOT_FOUND));

        return BugReportResponseDto.from(bugReport);
    }

    /**
     * 오류제보 전체 조회
     */
    public List<BugReportResponseDto> getAllBugReport() {
        return bugReportRepository.findAll()
                .stream()
                .map(BugReportResponseDto::from)
                .toList();
    }

    /**
     * 오류제보 생성
     */
    @Transactional
    public BugReportResponseDto createBugReport(BugReportRequestDto request) {
        BugReport bugReport = BugReport.create(
                request.category(),
                request.content(),
                request.email()
        );

        bugReportRepository.save(bugReport);

        return BugReportResponseDto.from(bugReport);
    }

    /**
     * 오류제보 삭제
     */
    @Transactional
    public void deleteBugReport(Long bugReportId) {
        BugReport bugReport = bugReportRepository.findById(bugReportId)
                .orElseThrow(() -> new MyException(ErrorCode.BUG_REPORT_NOT_FOUND));

        bugReportRepository.delete(bugReport);
    }
}
