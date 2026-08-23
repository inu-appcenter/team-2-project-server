package com.inuappcenter.team_2_project_server.domain.member.controller;

import com.inuappcenter.team_2_project_server.domain.member.dto.request.LoginRequestDto;
import com.inuappcenter.team_2_project_server.domain.member.dto.request.MemberCreateRequestDto;
import com.inuappcenter.team_2_project_server.domain.member.dto.request.MemberUpdateRequestDto;
import com.inuappcenter.team_2_project_server.domain.member.dto.response.LoginResponseDto;
import com.inuappcenter.team_2_project_server.domain.member.dto.response.MemberResponseDto;
import com.inuappcenter.team_2_project_server.domain.member.entity.Member;
import com.inuappcenter.team_2_project_server.global.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "회원", description = "회원 인증 및 프로필 관리 API")
public interface MemberApiSpecification {

    @Operation(summary = "로그인", description = "학번과 비밀번호로 로그인하고 JWT 토큰을 발급합니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "로그인 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "data": {
                                        "accessToken": "eyJhbGciOiJIUzI1NiJ9...",
                                        "refreshToken": "eyJhbGciOiJIUzI1NiJ9...",
                                        "accessTokenExpiresAt": "2026-08-18T12:00:00",
                                        "refreshTokenExpiresAt": "2026-08-25T12:00:00"
                                      },
                                      "code": null,
                                      "message": "로그인 성공"
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "요청 값 검증 실패",
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
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "학번 또는 비밀번호 불일치",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "data": null,
                                      "code": "INVALID_CREDENTIALS",
                                      "message": "학번 또는 비밀번호가 올바르지 않습니다."
                                    }
                                    """)
                    )
            )
    })
    ResponseEntity<ResponseDto<LoginResponseDto>> login(
            @Valid @RequestBody LoginRequestDto request
    );

    @Operation(summary = "유저 생성", description = "관리 목적의 유저 계정을 생성합니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "유저 생성 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "data": {
                                        "id": 1,
                                        "studentNumber": "20240001",
                                        "nickName": "홍길동",
                                        "department": "COMPUTER_ENGINEERING",
                                        "email": "student@example.com",
                                        "last_login_at": "2026-08-18T12:00:00"
                                      },
                                      "code": null,
                                      "message": "유저 생성 성공"
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "요청 값 검증 실패 또는 중복 학번",
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
    ResponseEntity<ResponseDto<MemberResponseDto>> createMember(
            @Valid @RequestBody MemberCreateRequestDto request
    );

    @Operation(summary = "유저 전체 조회", description = "등록된 전체 유저 목록을 조회합니다.")
    @ApiResponse(
            responseCode = "200",
            description = "전체 유저 조회 성공",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ResponseDto.class),
                    examples = @ExampleObject(value = """
                            {
                              "data": [
                                {
                                  "id": 1,
                                  "studentNumber": "20240001",
                                  "nickName": "홍길동",
                                  "department": "COMPUTER_ENGINEERING",
                                  "email": "student@example.com",
                                  "last_login_at": "2026-08-18T12:00:00"
                                }
                              ],
                              "code": null,
                              "message": "전체 유저 조회 성공"
                            }
                            """)
            )
    )
    ResponseEntity<ResponseDto<List<MemberResponseDto>>> getMemberAll();

    @Operation(summary = "유저 단일 조회", description = "유저 ID로 단일 유저 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "유저 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "data": {
                                        "id": 1,
                                        "studentNumber": "20240001",
                                        "nickName": "홍길동",
                                        "department": "COMPUTER_ENGINEERING",
                                        "email": "student@example.com",
                                        "last_login_at": "2026-08-18T12:00:00"
                                      },
                                      "code": null,
                                      "message": "유저 조회 성공"
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "존재하지 않는 유저",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "data": null,
                                      "code": "MEMBER_NOT_FOUND",
                                      "message": "존재하지 않는 유저입니다."
                                    }
                                    """)
                    )
            )
    })
    ResponseEntity<ResponseDto<MemberResponseDto>> getMember(
            @PathVariable Long memberId
    );

    @Operation(summary = "유저 프로필 수정", description = "인증된 유저의 닉네임, 학과, 이메일을 수정합니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "유저 프로필 수정 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "data": {
                                        "id": 1,
                                        "studentNumber": "20240001",
                                        "nickName": "새닉네임",
                                        "department": "COMPUTER_ENGINEERING",
                                        "email": "new-email@example.com",
                                        "last_login_at": "2026-08-18T12:00:00"
                                      },
                                      "code": null,
                                      "message": "유저 프로필 수정 성공"
                                    }
                                    """)
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
                    responseCode = "404",
                    description = "존재하지 않는 유저",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "data": null,
                                      "code": "MEMBER_NOT_FOUND",
                                      "message": "존재하지 않는 유저입니다."
                                    }
                                    """)
                    )
            )
    })
    ResponseEntity<ResponseDto<MemberResponseDto>> updateMember(
            @AuthenticationPrincipal Member member,
            @RequestBody MemberUpdateRequestDto request
    );

    @Operation(summary = "유저 삭제", description = "인증된 유저 계정을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "유저 삭제 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "data": 1,
                                      "code": null,
                                      "message": "유저 삭제 성공"
                                    }
                                    """)
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
                    responseCode = "404",
                    description = "존재하지 않는 유저",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "data": null,
                                      "code": "MEMBER_NOT_FOUND",
                                      "message": "존재하지 않는 유저입니다."
                                    }
                                    """)
                    )
            )
    })
    ResponseEntity<ResponseDto<Long>> deleteMember(
            @AuthenticationPrincipal Member member
    );
}
