package com.inuappcenter.team_2_project_server.global.dto;

import com.inuappcenter.team_2_project_server.global.error.ex.ErrorCode;

/**
 * 일관된 응답을 위한 전역 DTO
 */
public record ResponseDto<T>(
        T data,
        String code,
        String message
) {

    // 일반 응답 팩토리 메서드
    public static <T> ResponseDto<T> of(T data, String code, String message) {
        return new ResponseDto<>(data, code, message);
    }


    // 코드가 없는 응답 팩토리 메서드(일반적인 성공 응답)
    public static <T> ResponseDto<T> of(T data, String message) {
        return new ResponseDto<>(data, null, message);
    }


    // 에러 전용 팩토리 메서드
    public static <T> ResponseDto<T> error(ErrorCode errorCode) {
        return new ResponseDto<>(null, errorCode.getCode(), errorCode.getMessage());
    }
}
