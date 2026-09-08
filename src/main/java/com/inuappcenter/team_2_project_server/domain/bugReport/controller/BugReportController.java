package com.inuappcenter.team_2_project_server.domain.bugReport.controller;

import com.inuappcenter.team_2_project_server.domain.bugReport.dto.BugReportRequestDto;
import com.inuappcenter.team_2_project_server.domain.bugReport.dto.BugReportResponseDto;
import com.inuappcenter.team_2_project_server.domain.bugReport.service.BugReportService;
import com.inuappcenter.team_2_project_server.global.dto.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bug-report")
@RequiredArgsConstructor
public class BugReportController implements BugReportApiSpecification {

    private final BugReportService bugReportService;

    @GetMapping("/{bugReportId}")
    public ResponseEntity<ResponseDto<BugReportResponseDto>> getBugReport(
            @PathVariable Long bugReportId
    ) {
        BugReportResponseDto response = bugReportService.getBugReport(bugReportId);

        return ResponseEntity.ok(
                ResponseDto.of(response, "오류제보 조회 성공")
        );
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseDto<List<BugReportResponseDto>>> getAllBugReport(
    ) {
        List<BugReportResponseDto> responses = bugReportService.getAllBugReport();

        return ResponseEntity.ok(
                ResponseDto.of(responses, "오류제보 조회 성공")
        );
    }

    @PostMapping
    public ResponseEntity<ResponseDto<BugReportResponseDto>> createBugReport(
            @RequestBody @Valid BugReportRequestDto request
    ) {
        BugReportResponseDto response = bugReportService.createBugReport(request);

        return ResponseEntity.ok(
                ResponseDto.of(response, "오류제보 생성 성공")
        );
    }

    @DeleteMapping("/{bugReportId}")
    public ResponseEntity<ResponseDto<Long>> deleteBugReport(
            @PathVariable Long bugReportId
    ) {
        bugReportService.deleteBugReport(bugReportId);

        return ResponseEntity.ok(
                ResponseDto.of(bugReportId, "오류제보 삭제 성공")
        );
    }
}
