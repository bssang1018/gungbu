package com.kona.soogang;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(value = {IllegalStateException.class})
    public String someExceptionAdvice(Exception e){
        return "조회된 결과가 존재하지 않습니다. 입력하신 정보를 다시 확인해 주세요.";
    }
}
