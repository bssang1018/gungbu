package com.kona.soogang.exception;

import com.fasterxml.jackson.core.JsonParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.BindException;

@RestControllerAdvice
public class TestExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    //커스텀 예외
    @ExceptionHandler({TestException.class})
    public ResponseEntity<String> handleValidityException(TestException e) {
        return new ResponseEntity(e.getTestHttpResponseCode().getMessage(),e.getTestHttpResponseCode().getHttpStatus());
    }

    //공백입력 예외
    @ExceptionHandler(value = IllegalStateException.class)
    public ResponseEntity<String> illegalState(){
        return new ResponseEntity<>("입력값이 잘못됐습니다. (공백은 입력할 수 없습니다)", HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = NullPointerException.class)
    public ResponseEntity<String> nullPoint(){
        return new ResponseEntity<>("필요한 데이터를 모두 입력해 주세요.", HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = JsonParseException.class)
    public ResponseEntity<String> parseEx(){
        return new ResponseEntity<>("알맞은 형식으로 데이터를 입력해 주세요.", HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = PropertyReferenceException.class)
    public ResponseEntity<String> sortEx(){
        return new ResponseEntity<>("정렬할 컬럼의 이름을 정확하게 입력해 주세요.", HttpStatus.UNPROCESSABLE_ENTITY);
    }


//    @ExceptionHandler(value = IllegalArgumentException.class)
//    public ResponseEntity<String> duplicate(){
//        return new ResponseEntity<>("중복된 데이터가 검색되었습니다. 다른 값을 입력해 주세요.", HttpStatus.INTERNAL_SERVER_ERROR);
//    }

//    @ExceptionHandler(value = ClassCastException.class)
//    public ResponseEntity<String> maxIssue(){
//        return new ResponseEntity<>("인원이 초과했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
//    }




}
