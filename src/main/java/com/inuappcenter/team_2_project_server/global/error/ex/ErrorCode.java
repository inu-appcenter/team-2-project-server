package com.inuappcenter.team_2_project_server.global.error.ex;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "INVALID_INPUT", "잘못된 요청입니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
