package com.inuappcenter.team_2_project_server.global.dto;

import com.inuappcenter.team_2_project_server.global.error.ex.ErrorCode;

public record ResponseDto<T>(
        T data,
        String code,
        String message
) {

    // 일반 응답 팩토리 메서드
    public static <T> ResponseDto<T> of(T data, String code, String message) {
        return new ResponseDto<>(data, code, message);
    }

    
    // 메시지가 없는 응답 팩토리 메서드
    public static <T> ResponseDto<T> of(T data, String code) {
        return new ResponseDto<>(data, code, null);
    }


    // 에러 전용 팩토리 메서드
    public static <T> ResponseDto<T> error(ErrorCode errorCode) {
        return new ResponseDto<>(null, errorCode.getCode(), errorCode.getMessage());
    }
}
