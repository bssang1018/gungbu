package com.kona.soogang.controller;

import com.kona.soogang.dto.*;
import com.kona.soogang.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequestMapping("/student")
@RestController
public class StudentController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //@RequiredArgsConstructor 사용하지 않고, 직접 생성자 명시
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    //학생등록
    @PostMapping(value = "/join", produces = "application/json; charset=UTF-8")
    public ResponseEntity<StudentDto> studentJoin(@Valid StudentReq studentReq) {
        logger.info("회원가입 파라미터 :: " + studentReq);

        ResponseEntity responseEntity = studentService.studentJoin(studentReq);
        //응답 로그
        logger.info("RESPONSE :: " + responseEntity);

        return responseEntity;
    }

    //수강신청
    @PostMapping(value = "/lectureRegister", produces = "application/json; charset=UTF-8")
    public ResponseEntity<RegisterDto> lectureRegister(@Valid @RequestBody RegisterReq registerReq) {
        logger.info("수강신청 파라미터 확인 :: " + registerReq.toString());
        return studentService.lectureRegister(registerReq);
    }

    //수강취소
    @PostMapping(value = "/registerCancel", produces = "application/json; charset=UTF-8")
    public ResponseEntity<RegisterDto> registerCancel(@Valid @RequestBody RegisterReq registerReq) {
        logger.info("수강취소 파라미터 확인 :: " + registerReq.toString());
        return studentService.registerCancel(registerReq);
    }

    //수강신청 조회
    @GetMapping(value = "/registerList/{email}/{page}/{size}/{sort}")
    public ResponseEntity<Page<RegisterDto>> registerList(@PathVariable @Email @NotBlank String email, int page, int size, @NotBlank String sort) {
        logger.info("수강 신청 리스트 조회:: " + email + "/" + page + "/" + size + "/");
        return studentService.registerList(email, page, size, sort);
    }

    //나를 추천한 강사의 이름 확인
    @GetMapping(value = "/recommendedMe/{email}")
    public ResponseEntity<String> recommendedMe(@PathVariable @Email @NotBlank String email) {
        logger.info("추천 강사를 확인할 이메일:: " + email);
        return studentService.recommendedMe(email);
    }



}
