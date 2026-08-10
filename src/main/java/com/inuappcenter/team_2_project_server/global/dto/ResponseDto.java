package com.inuappcenter.team_2_project_server.global.dto;

public record ResponseDto<T>(
        T data,
        String code,
        String message
) {

    public static <T> ResponseDto<T> of(T data, String code, String message) {
        return new ResponseDto<>(data, code, message);
    }

    public static <T> ResponseDto<T> of(T data, String code) {
        return new ResponseDto<>(data, code, null);
    }
}
