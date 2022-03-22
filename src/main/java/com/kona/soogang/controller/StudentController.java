package com.kona.soogang.controller;

import com.kona.soogang.dto.*;
import com.kona.soogang.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<StudentDto> studentJoin(@RequestBody StudentReq studentReq) {
        logger.info("회원가입 파라미터 :: " + studentReq.getEmail()+ " / "+ studentReq.getName());
        return studentService.studentJoin(studentReq);
    }

    //수강신청
    @PostMapping(value = "/lectureRegister", produces = "application/json; charset=UTF-8")
    public ResponseEntity<RegisterDto> lectureRegister(@RequestBody RegisterReq registerReq) {
        logger.info("수강신청 파라미터 확인 :: " + registerReq.getLectureName() +" / "+registerReq.getEmail());
        return studentService.lectureRegister(registerReq);
    }

    //수강취소
    @PostMapping(value = "/registerCancel", produces = "application/json; charset=UTF-8")
    public ResponseEntity<RegisterDto> registerCancel(@RequestBody RegisterReq registerReq) {
        logger.info("수강취소 파라미터 확인 :: "  + registerReq.getLectureName() +" / "+registerReq.getEmail());
        return studentService.registerCancel(registerReq);
    }

    //수강신청 조회
    @GetMapping(value = "/registerList")
    public List<RegisterResponse> registerList(@RequestParam String email, Pageable pageable) {
        return studentService.registerList(email, pageable);
    }

    //나를 추천한 강사의 이름 확인
    @GetMapping(value = "/recommendedMe/{email}")
    public String recommendedMe(@PathVariable String email) {
        logger.info("추천 강사를 확인할 이메일:: " + email);
        return studentService.recommendedMe(email);
    }
}
