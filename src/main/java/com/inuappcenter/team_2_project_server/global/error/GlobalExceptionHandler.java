package com.inuappcenter.team_2_project_server.global.error;

import com.inuappcenter.team_2_project_server.global.dto.ResponseDto;
import com.inuappcenter.team_2_project_server.global.error.ex.MyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 서비스(커스텀) 에러를 잡는 핸들러
     * - 예시 응답
     * {
     * "data": null,
     * "code": "INVALID_INPUT",
     * "message": "잘못된 요청입니다."
     * }
     */
    @ExceptionHandler(MyException.class)
    public ResponseEntity<ResponseDto<Integer>> handleMyException(MyException e) {
        log.error("예외 발생 msg:{}", e.getErrorCode().getMessage());
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(ResponseDto.of(null, e.getErrorCode().getCode(), e.getErrorCode().getMessage()));
    }
}
