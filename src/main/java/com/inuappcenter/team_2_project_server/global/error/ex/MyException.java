package com.inuappcenter.team_2_project_server.global.error.ex;

import lombok.Getter;

@Getter
public class MyException extends RuntimeException {

    private final ErrorCode errorCode;

    public MyException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
