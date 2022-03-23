package com.kona.soogang.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum TestHttpResponseCode {
    RESULT_NOT_FOUND(HttpStatus.UNPROCESSABLE_ENTITY, "조회된 결과가 없습니다."),
    IDOREMAIL_DUPLICATE(HttpStatus.UNPROCESSABLE_ENTITY, "중복된 아이디 또는 이메일입니다"),
    REGISTER_DUPLICATE(HttpStatus.UNPROCESSABLE_ENTITY, "강의는 중복 수강신청 할 수 없습니다."),
    MAX_ISSUE(HttpStatus.UNPROCESSABLE_ENTITY, "수강신청 가능 인원을 초과했습니다."),
    DUPLICATE_REQUEST(HttpStatus.UNPROCESSABLE_ENTITY, "이미 처리된 요청입니다."),
    CANT_CLOSE(HttpStatus.UNPROCESSABLE_ENTITY, "수강신청 인원이 2명 이상이기 때문에, 폐강할 수 없습니다."),
    PERSON_ISSUE(HttpStatus.UNPROCESSABLE_ENTITY, "수강인원은 최소 1명 부터 최대 100명 까지 설정 가능합니다.")
    ;
    private HttpStatus httpStatus;
    private String message;

    TestHttpResponseCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }


}