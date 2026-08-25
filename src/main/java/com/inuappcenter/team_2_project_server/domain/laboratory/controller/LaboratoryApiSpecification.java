package com.inuappcenter.team_2_project_server.domain.laboratory.controller;

import com.inuappcenter.team_2_project_server.domain.laboratory.dto.request.LaboratoryCreateRequestDto;
import com.inuappcenter.team_2_project_server.domain.laboratory.dto.request.LaboratoryUpdateRequestDto;
import com.inuappcenter.team_2_project_server.domain.laboratory.dto.response.LaboratoryResponseDto;
import com.inuappcenter.team_2_project_server.global.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "연구실", description = "연구실 관리 및 편람 엑셀 동기화 API")
public interface LaboratoryApiSpecification {

    @Operation(
            summary = "연구실 편람 엑셀 import",
            description = """
                    연구실 편람 엑셀 파일을 업로드하여 교수, 연구실, 연구분야 키워드를 저장합니다.
                    연구실 시트와 교수정보 시트를 포함한 .xlsx 파일만 지원합니다.
                    이미 저장된 연구실은 중복 저장하지 않고 건너뜁니다.
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "연구실 편람 동기화 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "data": null,
                                      "code": null,
                                      "message": "연구실 편람 동기화 완료"
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "빈 파일, 지원하지 않는 확장자, 엑셀 형식 오류, 교수 매칭 실패",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = {
                                    @ExampleObject(
                                            name = "잘못된 엑셀 헤더",
                                            value = """
                                                    {
                                                      "data": null,
                                                      "code": "INVALID_EXCEL_HEADER",
                                                      "message": "엑셀 헤더 형식이 올바르지 않습니다."
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "교수 매칭 실패",
                                            value = """
                                                    {
                                                      "data": null,
                                                      "code": "PROFESSOR_NOT_FOUND_IN_EXCEL",
                                                      "message": "연구실 시트의 교수를 교수정보 시트에서 찾을 수 없습니다."
                                                    }
                                                    """
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "인증 토큰 누락, 만료 또는 유효하지 않은 토큰",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "data": null,
                                      "code": "TOKEN_INVALID",
                                      "message": "유효하지 않은 토큰입니다."
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "관리자 권한 없음",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "data": null,
                                      "code": "ACCESS_DENIED",
                                      "message": "접근 권한이 없습니다."
                                    }
                                    """)
                    )
            )
    })
    ResponseEntity<ResponseDto<Void>> importLaboratory(
            @Parameter(
                    description = "연구실 편람 .xlsx 파일",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE,
                            schema = @Schema(type = "string", format = "binary")
                    )
            )
            @RequestPart MultipartFile file
    );

    @Operation(summary = "연구실 생성", description = "교수 ID를 기준으로 연구실을 수동 생성합니다.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "생성할 연구실 정보. 연구분야는 쉼표 문자열이 아니라 배열로 전달합니다.",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = LaboratoryCreateRequestDto.class),
                    examples = @ExampleObject(value = """
                            {
                              "college": "COLLEGE_OF_INFORMATION_TECHNOLOGY",
                              "department": "COMPUTER_ENGINEERING",
                              "labName": "소프트웨어공학 연구실",
                              "location": "7호관 401호",
                              "capacity": {
                                "graduateStudentCount": 6,
                                "undergraduateStudentCount": 7
                              },
                              "introduction": "소프트웨어 품질과 개발 프로세스를 연구합니다.",
                              "professorId": 1,
                              "labUrl": "https://example.com/lab",
                              "researchAreas": [
                                "소프트웨어공학",
                                "인공지능"
                              ]
                            }
                            """)
            )
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "연구실 생성 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "data": {
                                        "id": 1,
                                        "college": "COLLEGE_OF_INFORMATION_TECHNOLOGY",
                                        "department": "COMPUTER_ENGINEERING",
                                        "labName": "소프트웨어공학 연구실",
                                        "location": "7호관 401호",
                                        "capacity": {
                                          "graduateStudentCount": 6,
                                          "undergraduateStudentCount": 7
                                        },
                                        "introduction": "소프트웨어 품질과 개발 프로세스를 연구합니다.",
                                        "professor": {
                                          "id": 1,
                                          "positionRaw": "교수",
                                          "college": "COLLEGE_OF_INFORMATION_TECHNOLOGY",
                                          "department": "COMPUTER_ENGINEERING",
                                          "name": "홍길동",
                                          "phoneNumber": "032-835-0000",
                                          "email": "professor@example.com"
                                        },
                                        "labUrl": "https://example.com/lab",
                                        "researchAreas": [
                                          "소프트웨어공학",
                                          "인공지능"
                                        ]
                                      },
                                      "code": null,
                                      "message": "연구실 생성 성공"
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "존재하지 않는 교수 또는 중복 연구실",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = {
                                    @ExampleObject(
                                            name = "존재하지 않는 교수",
                                            value = """
                                                    {
                                                      "data": null,
                                                      "code": "PROFESSOR_NOT_FOUND",
                                                      "message": "존재하지 않는 교수입니다."
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "중복 연구실",
                                            value = """
                                                    {
                                                      "data": null,
                                                      "code": "DUPLICATED_LAB",
                                                      "message": "중복된 연구실입니다."
                                                    }
                                                    """
                                    )
                            }
                    )
            )
    })
    ResponseEntity<ResponseDto<LaboratoryResponseDto>> createLaboratory(
            @RequestBody LaboratoryCreateRequestDto request
    );

    @Operation(summary = "연구실 단건 조회", description = "연구실 ID로 단일 연구실 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "연구실 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "data": {
                                        "id": 1,
                                        "college": "COLLEGE_OF_INFORMATION_TECHNOLOGY",
                                        "department": "COMPUTER_ENGINEERING",
                                        "labName": "소프트웨어공학 연구실",
                                        "location": "7호관 401호",
                                        "capacity": {
                                          "graduateStudentCount": 6,
                                          "undergraduateStudentCount": 7
                                        },
                                        "introduction": "소프트웨어 품질과 개발 프로세스를 연구합니다.",
                                        "professor": {
                                          "id": 1,
                                          "positionRaw": "교수",
                                          "college": "COLLEGE_OF_INFORMATION_TECHNOLOGY",
                                          "department": "COMPUTER_ENGINEERING",
                                          "name": "홍길동",
                                          "phoneNumber": "032-835-0000",
                                          "email": "professor@example.com"
                                        },
                                        "labUrl": "https://example.com/lab",
                                        "researchAreas": [
                                          "소프트웨어공학",
                                          "인공지능"
                                        ]
                                      },
                                      "code": null,
                                      "message": "연구실 조회 성공"
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "존재하지 않는 연구실",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "data": null,
                                      "code": "LABORATORY_NOT_FOUND",
                                      "message": "존재하지 않는 연구실입니다."
                                    }
                                    """)
                    )
            )
    })
    ResponseEntity<ResponseDto<LaboratoryResponseDto>> getLaboratory(
            @PathVariable Long laboratoryId
    );

    @Operation(summary = "연구실 전체 조회", description = "등록된 전체 연구실 목록을 조회합니다.")
    @ApiResponse(
            responseCode = "200",
            description = "전체 연구실 조회 성공",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ResponseDto.class),
                    examples = @ExampleObject(value = """
                            {
                              "data": [
                                {
                                  "id": 1,
                                  "college": "COLLEGE_OF_INFORMATION_TECHNOLOGY",
                                  "department": "COMPUTER_ENGINEERING",
                                  "labName": "소프트웨어공학 연구실",
                                  "location": "7호관 401호",
                                  "capacity": {
                                    "graduateStudentCount": 6,
                                    "undergraduateStudentCount": 7
                                  },
                                  "introduction": "소프트웨어 품질과 개발 프로세스를 연구합니다.",
                                  "professor": {
                                    "id": 1,
                                    "positionRaw": "교수",
                                    "college": "COLLEGE_OF_INFORMATION_TECHNOLOGY",
                                    "department": "COMPUTER_ENGINEERING",
                                    "name": "홍길동",
                                    "phoneNumber": "032-835-0000",
                                    "email": "professor@example.com"
                                  },
                                  "labUrl": "https://example.com/lab",
                                  "researchAreas": [
                                    "소프트웨어공학",
                                    "인공지능"
                                  ]
                                }
                              ],
                              "code": null,
                              "message": "전체 연구실 조회 성공"
                            }
                            """)
            )
    )
    ResponseEntity<ResponseDto<List<LaboratoryResponseDto>>> getAllLaboratory();

    @Operation(
            summary = "연구실 수정",
            description = """
                    연구실 ID로 연구실 정보를 수정합니다.
                    요청에 포함된 값만 수정하며, 연구분야는 쉼표 문자열이 아니라 배열로 전달합니다.
                    """
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "수정할 연구실 정보. 변경하지 않을 필드는 요청에서 제외합니다.",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = LaboratoryUpdateRequestDto.class),
                    examples = @ExampleObject(value = """
                            {
                              "location": "7호관 402호",
                              "capacity": {
                                "undergraduateStudentCount": 8
                              },
                              "researchAreas": [
                                "소프트웨어공학",
                                "데이터마이닝"
                              ]
                            }
                            """)
            )
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "연구실 수정 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "data": {
                                        "id": 1,
                                        "college": "COLLEGE_OF_INFORMATION_TECHNOLOGY",
                                        "department": "COMPUTER_ENGINEERING",
                                        "labName": "소프트웨어공학 연구실",
                                        "location": "7호관 402호",
                                        "capacity": {
                                          "graduateStudentCount": 6,
                                          "undergraduateStudentCount": 8
                                        },
                                        "introduction": "소프트웨어 품질과 개발 프로세스를 연구합니다.",
                                        "professor": {
                                          "id": 1,
                                          "positionRaw": "교수",
                                          "college": "COLLEGE_OF_INFORMATION_TECHNOLOGY",
                                          "department": "COMPUTER_ENGINEERING",
                                          "name": "홍길동",
                                          "phoneNumber": "032-835-0000",
                                          "email": "professor@example.com"
                                        },
                                        "labUrl": "https://example.com/lab",
                                        "researchAreas": [
                                          "소프트웨어공학",
                                          "데이터마이닝"
                                        ]
                                      },
                                      "code": null,
                                      "message": "연구실 수정 성공"
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "존재하지 않는 연구실",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "data": null,
                                      "code": "LABORATORY_NOT_FOUND",
                                      "message": "존재하지 않는 연구실입니다."
                                    }
                                    """)
                    )
            )
    })
    ResponseEntity<ResponseDto<LaboratoryResponseDto>> updateLaboratory(
            @PathVariable Long laboratoryId,
            @RequestBody LaboratoryUpdateRequestDto request
    );

    @Operation(summary = "연구실 삭제", description = "연구실 ID로 연구실을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "연구실 삭제 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "data": 1,
                                      "code": null,
                                      "message": "연구실 삭제 완료"
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "존재하지 않는 연구실",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "data": null,
                                      "code": "LABORATORY_NOT_FOUND",
                                      "message": "존재하지 않는 연구실입니다."
                                    }
                                    """)
                    )
            )
    })
    ResponseEntity<ResponseDto<Long>> deleteLaboratory(
            @PathVariable Long laboratoryId
    );

    @Operation(summary = "연구실 검색", description = "연구실명 또는 교수명에 검색어가 포함된 연구실 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "연구실 검색 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "data": [
                                        {
                                          "id": 1,
                                          "college": "COLLEGE_OF_INFORMATION_TECHNOLOGY",
                                          "department": "COMPUTER_ENGINEERING",
                                          "labName": "소프트웨어공학 연구실",
                                          "location": "7호관 401호",
                                          "capacity": {
                                            "graduateStudentCount": 6,
                                            "undergraduateStudentCount": 7
                                          },
                                          "introduction": "소프트웨어 품질과 개발 프로세스를 연구합니다.",
                                          "professor": {
                                            "id": 1,
                                            "positionRaw": "교수",
                                            "college": "COLLEGE_OF_INFORMATION_TECHNOLOGY",
                                            "department": "COMPUTER_ENGINEERING",
                                            "name": "홍길동",
                                            "phoneNumber": "032-835-0000",
                                            "email": "professor@example.com"
                                          },
                                          "labUrl": "https://example.com/lab",
                                          "researchAreas": [
                                            "소프트웨어공학",
                                            "인공지능"
                                          ]
                                        }
                                      ],
                                      "code": null,
                                      "message": "연구실 검색 성공"
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "검색어가 비어 있음",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "data": null,
                                      "code": "NO_SEARCH_KEYWORD",
                                      "message": "허용하지 않는 검색어입니다."
                                    }
                                    """)
                    )
            )
    })
    ResponseEntity<ResponseDto<List<LaboratoryResponseDto>>> searchLaboratory(
            @Parameter(description = "연구실명 또는 교수명 검색어", required = true, example = "홍길동")
            @RequestParam String keyword
    );
}
