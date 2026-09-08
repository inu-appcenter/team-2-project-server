package com.inuappcenter.team_2_project_server.domain.bugReport.controller;

import com.inuappcenter.team_2_project_server.domain.bugReport.dto.BugReportRequestDto;
import com.inuappcenter.team_2_project_server.domain.bugReport.dto.BugReportResponseDto;
import com.inuappcenter.team_2_project_server.global.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "오류 제보", description = "허위정보 / 버그 / 기타 오류를 사용자가 제보하고, 관리자가 조회·삭제하는 API")
public interface BugReportApiSpecification {

    @Operation(
            summary = "오류 제보 단건 조회",
            description = "오류 제보 ID로 한 건을 조회합니다. 관리자용입니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "오류 제보 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "data": {
                                        "id": 1,
                                        "category": "ERROR",
                                        "content": "연구실 상세 페이지에서 리뷰 목록이 안 보여요.",
                                        "email": "reporter@example.com"
                                      },
                                      "code": null,
                                      "message": "오류제보 조회 성공"
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "존재하지 않는 오류 제보",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "data": null,
                                      "code": "BUG_REPORT_NOT_FOUND",
                                      "message": "오류 제보가 존재하지 않습니다."
                                    }
                                    """)
                    )
            )
    })
    ResponseEntity<ResponseDto<BugReportResponseDto>> getBugReport(
            @Parameter(description = "조회할 오류 제보 ID", required = true, example = "1")
            @PathVariable Long bugReportId
    );

    @Operation(
            summary = "오류 제보 전체 조회",
            description = "제보된 모든 오류를 조회합니다. 제보자 이메일이 포함되므로 관리자용입니다."
    )
    @ApiResponse(
            responseCode = "200",
            description = "오류 제보 목록 조회 성공",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ResponseDto.class),
                    examples = @ExampleObject(value = """
                            {
                              "data": [
                                {
                                  "id": 2,
                                  "category": "INVALID_INFO",
                                  "content": "김OO 교수님 연구실 학과 정보가 잘못되어 있어요.",
                                  "email": null
                                },
                                {
                                  "id": 1,
                                  "category": "ERROR",
                                  "content": "연구실 상세 페이지에서 리뷰 목록이 안 보여요.",
                                  "email": "reporter@example.com"
                                }
                              ],
                              "code": null,
                              "message": "오류제보 조회 성공"
                            }
                            """)
            )
    )
    ResponseEntity<ResponseDto<List<BugReportResponseDto>>> getAllBugReport();

    @Operation(
            summary = "오류 제보 생성",
            description = """
                    사용자가 오류를 제보합니다.
                    category 는 INVALID_INFO(허위정보) / ERROR(버그·오류) / ETC(기타) 중 하나입니다.
                    email 은 선택값이며, 남기면 관리자가 회신할 수 있습니다.
                    """
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "제보 분류, 내용, (선택) 회신 이메일",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = BugReportRequestDto.class),
                    examples = {
                            @ExampleObject(name = "버그 제보 (이메일 포함)", value = """
                                    {
                                      "category": "ERROR",
                                      "content": "연구실 상세 페이지에서 리뷰 목록이 안 보여요.",
                                      "email": "reporter@example.com"
                                    }
                                    """),
                            @ExampleObject(name = "허위정보 제보 (이메일 없이)", value = """
                                    {
                                      "category": "INVALID_INFO",
                                      "content": "김OO 교수님 연구실 학과 정보가 잘못되어 있어요.",
                                      "email": null
                                    }
                                    """)
                    }
            )
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "오류 제보 생성 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "data": {
                                        "id": 3,
                                        "category": "ERROR",
                                        "content": "연구실 상세 페이지에서 리뷰 목록이 안 보여요.",
                                        "email": "reporter@example.com"
                                      },
                                      "code": null,
                                      "message": "오류제보 생성 성공"
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "요청 값 검증 실패 (category 누락, content 비어있음 등)",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "data": null,
                                      "code": "INVALID_INPUT",
                                      "message": "잘못된 요청입니다."
                                    }
                                    """)
                    )
            )
    })
    ResponseEntity<ResponseDto<BugReportResponseDto>> createBugReport(
            @RequestBody @Valid BugReportRequestDto request
    );

    @Operation(
            summary = "오류 제보 삭제",
            description = "처리 완료된 오류 제보를 삭제합니다. 관리자용입니다. 응답 data 는 삭제된 제보 ID 입니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "오류 제보 삭제 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "data": 1,
                                      "code": null,
                                      "message": "오류제보 삭제 성공"
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "존재하지 않는 오류 제보",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "data": null,
                                      "code": "BUG_REPORT_NOT_FOUND",
                                      "message": "오류 제보가 존재하지 않습니다."
                                    }
                                    """)
                    )
            )
    })
    ResponseEntity<ResponseDto<Long>> deleteBugReport(
            @Parameter(description = "삭제할 오류 제보 ID", required = true, example = "1")
            @PathVariable Long bugReportId
    );
}
