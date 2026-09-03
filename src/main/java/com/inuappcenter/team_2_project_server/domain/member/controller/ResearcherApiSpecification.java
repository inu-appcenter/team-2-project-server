package com.inuappcenter.team_2_project_server.domain.member.controller;

import com.inuappcenter.team_2_project_server.domain.member.dto.request.ResearcherRegisterRequestDto;
import com.inuappcenter.team_2_project_server.domain.member.dto.response.ResearcherResponseDto;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "연구자", description = "회원을 특정 연구실 소속 연구자로 등록·조회하는 API")
public interface ResearcherApiSpecification {

    @Operation(
            summary = "연구자 등록",
            description = """
                    회원(memberId)을 지정한 연구실(laboratoryId) 소속 연구자로 등록합니다.
                    회원당 1명의 연구자만 등록할 수 있습니다.
                    ※ 인증/인가 방식 미확정: 현재는 요청 본문의 memberId 를 그대로 신뢰합니다.
                    """
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "등록할 회원 ID, 소속 연구실 ID, 신원 확인용 실명",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ResearcherRegisterRequestDto.class),
                    examples = @ExampleObject(value = """
                            {
                              "memberId": 1,
                              "laboratoryId": 465,
                              "name": "홍길동"
                            }
                            """)
            )
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "연구자 등록 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "data": {
                                        "id": 1,
                                        "memberId": 1,
                                        "studentNumber": "20240001",
                                        "laboratoryId": 465,
                                        "laboratoryName": "소프트웨어공학 연구실",
                                        "validateYN": false
                                      },
                                      "code": null,
                                      "message": "연구자 등록 성공"
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "요청 값 검증 실패 또는 이미 등록된 연구자",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "data": null,
                                      "code": "RESEARCHER_ALREADY_EXISTS",
                                      "message": "이미 존재하는 연구자입니다."
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "존재하지 않는 회원 또는 연구실",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = {
                                    @ExampleObject(
                                            name = "존재하지 않는 회원",
                                            value = """
                                                    {
                                                      "data": null,
                                                      "code": "MEMBER_NOT_FOUND",
                                                      "message": "존재하지 않는 유저입니다."
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "존재하지 않는 연구실",
                                            value = """
                                                    {
                                                      "data": null,
                                                      "code": "LABORATORY_NOT_FOUND",
                                                      "message": "존재하지 않는 연구실입니다."
                                                    }
                                                    """
                                    )
                            }
                    )
            )
    })
    ResponseEntity<ResponseDto<ResearcherResponseDto>> register(
            @Valid @RequestBody ResearcherRegisterRequestDto request
    );

    @Operation(
            summary = "연구자 조회",
            description = "회원 ID로 해당 회원의 연구자 등록 정보를 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "연구자 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "data": {
                                        "id": 1,
                                        "memberId": 1,
                                        "studentNumber": "20240001",
                                        "laboratoryId": 465,
                                        "laboratoryName": "소프트웨어공학 연구실",
                                        "validateYN": false
                                      },
                                      "code": null,
                                      "message": "연구자 조회 성공"
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "해당 회원의 연구자 등록 정보 없음",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "data": null,
                                      "code": "RESEARCHER_NOT_FOUND",
                                      "message": "존재하지 않는 연구생입니다."
                                    }
                                    """)
                    )
            )
    })
    ResponseEntity<ResponseDto<ResearcherResponseDto>> getResearcher(
            @Parameter(description = "조회할 회원 ID", required = true, example = "1")
            @RequestParam Long memberId
    );
}
