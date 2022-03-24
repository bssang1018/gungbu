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
    public ResponseEntity<StudentDto> studentJoin(@Valid @RequestBody StudentReq studentReq) {
        logger.info("회원가입 파라미터 :: " + studentReq.getEmail() + " / " + studentReq.getName());
        return studentService.studentJoin(studentReq);
    }

    //수강신청
    @PostMapping(value = "/lectureRegister", produces = "application/json; charset=UTF-8")
    public ResponseEntity<RegisterDto> lectureRegister(@Valid @RequestBody RegisterReq registerReq) {
        logger.info("수강신청 파라미터 확인 :: " + registerReq.getLectureName() + " / " + registerReq.getEmail());
        return studentService.lectureRegister(registerReq);
    }

    //수강취소
    @PostMapping(value = "/registerCancel", produces = "application/json; charset=UTF-8")
    public ResponseEntity<RegisterDto> registerCancel(@Valid @RequestBody RegisterReq registerReq) {
        logger.info("수강취소 파라미터 확인 :: " + registerReq.getLectureName() + " / " + registerReq.getEmail());
        return studentService.registerCancel(registerReq);
    }

    //수강신청 조회
    @GetMapping(value = "/registerList")
    public ResponseEntity<Page<RegisterDto>> registerList(@RequestParam String email, int page, int size, String sort) {
        logger.info("수강 신청 리스트 조회:: " + email + "/" + page + "/" + size + "/");
        if (!isValidEmail(email)){
            throw new IllegalStateException();
        }
        if (sort == null || sort.trim().isEmpty() || Integer.valueOf(page) == null || Integer.valueOf(size) == null || size <= 0) {
            throw new IllegalStateException();
        }
        return studentService.registerList(email, page, size, sort);
    }

    //나를 추천한 강사의 이름 확인
    @GetMapping(value = "/recommendedMe/")
    public ResponseEntity<String> recommendedMe(@RequestParam String email) {
        logger.info("추천 강사를 확인할 이메일:: " + email);
        if (!isValidEmail(email)){
            throw new IllegalStateException();
        }
        return studentService.recommendedMe(email);
    }


    /** * Comment : 정상적인 이메일 인지 검증. */
    public static boolean isValidEmail(String email) {
        boolean err = false;
        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        if(m.matches()) {
            err = true;
        }
        return err;
    }




}
