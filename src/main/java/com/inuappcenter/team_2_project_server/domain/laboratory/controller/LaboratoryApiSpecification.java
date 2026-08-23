package com.inuappcenter.team_2_project_server.domain.laboratory.controller;

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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "연구실", description = "연구실 편람 엑셀 동기화 API")
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
}
