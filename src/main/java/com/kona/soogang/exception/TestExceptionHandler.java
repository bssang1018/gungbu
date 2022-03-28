package com.kona.soogang.exception;

import com.fasterxml.jackson.core.JsonParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class TestExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    //커스텀 예외
    @ExceptionHandler(value = TestException.class)
    public ResponseEntity<String> handleValidityException(TestException e) {
        return new ResponseEntity(e.getTestHttpResponseCode().getMessage(),e.getTestHttpResponseCode().getHttpStatus());
    }

    //사용됨 이메일 형식이 아닌경우, 공백, null
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<String> dataVali(){
        return new ResponseEntity<>("데이터를 올바르게 입력해 주세요.", HttpStatus.UNPROCESSABLE_ENTITY);
    }

    //공백입력 예외 (사용됨)
    @ExceptionHandler(value = IllegalStateException.class)
    public ResponseEntity<String> illegalState(){
        return new ResponseEntity<>("입력값이 잘못됐습니다.", HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = NullPointerException.class)
    public ResponseEntity<String> nullPoint(){
        return new ResponseEntity<>("필요한 데이터를 모두 입력해 주세요.", HttpStatus.UNPROCESSABLE_ENTITY);
    }

    //사용됨
    @ExceptionHandler(value = JsonParseException.class)
    public ResponseEntity<String> parseEx(){
        return new ResponseEntity<>("알맞은 형식으로 데이터를 입력해 주세요.", HttpStatus.UNPROCESSABLE_ENTITY);
    }

    //사용됨 엔티티 컬럼명 틀린경우
    @ExceptionHandler(value = PropertyReferenceException.class)
    public ResponseEntity<String> sortEx(){
        return new ResponseEntity<>("정렬할 컬럼의 이름을 정확하게 입력해 주세요.", HttpStatus.UNPROCESSABLE_ENTITY);
    }




}
