package com.kona.soogang.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TestExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<String> duplicate(){
        return new ResponseEntity<>("중복된 데이터가 검색되었습니다. 다른 값을 입력해 주세요.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = IllegalStateException.class)
    public ResponseEntity<String> noResult(){
        return new ResponseEntity<>("조회된 결과가 없습니다. 다른 값으로 검색해 주세요.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = ClassCastException.class)
    public ResponseEntity<String> maxIssue(){
        return new ResponseEntity<>("인원이 초과했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value =  NullPointerException.class)
    public ResponseEntity<String> paramIssue(){
        return new ResponseEntity<>("공백은 입력할 수 없습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
