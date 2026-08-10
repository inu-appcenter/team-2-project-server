package com.inuappcenter.team_2_project_server.global.dto;

import lombok.Builder;
import lombok.Getter;

public record ResponseDto<T> (
        T data,
        String message
){

    public static <T> ResponseDto<T> of(T data, String message){
        return new ResponseDto<>(data, message);
    }

    public static <T> ResponseDto<T> of(T data){
        return new ResponseDto<>(data, null);
    }
}
