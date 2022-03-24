package com.kona.soogang.controller;

import com.kona.soogang.dto.*;
import com.kona.soogang.service.TeacherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequestMapping("/teacher")
@RestController
public class TeacherController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    //@RequiredArgsConstructor 사용하지 않고, 직접 생성자 명시
    private final TeacherService teacherService;
    public TeacherController(TeacherService teacherService){
        this.teacherService = teacherService;
    }

    //강사 가입
    @PostMapping(value = "/join", produces = "application/json; charset=UTF-8")
    public ResponseEntity<TeacherDto> teacherJoin(@Valid @RequestBody TeacherReq teacherReq) {
        logger.info("teacher join 파라미터 확인 :: " + teacherReq.getTeacherId() + teacherReq.getTeacherName());
        return teacherService.teacherJoin(teacherReq);
    }

    //학생 추천
    @PostMapping(value = "/recommend", produces = "application/json; charset=UTF-8")
    public ResponseEntity<StudentDto> recommend(@Valid @RequestBody RecommendReq recommendReq) {
        logger.info("추천할 이메일 :: " +recommendReq.getTeacherId()+" / " + recommendReq.getEmail());
        return teacherService.recommend(recommendReq);
    }

    //강의 등록
    @PostMapping(value = "/lectureInsert", produces = "application/json; charset=UTF-8")
    public ResponseEntity<LectureDto> lectureInsert(@Valid @RequestBody LectureReq lectureReq) {
        logger.info("강의 등록 파라미터 확인 :: " + lectureReq.getLectureName() + " / " + lectureReq.getTeacherId() + " / " + lectureReq.getMaxPerson());
        return teacherService.lectureInsert(lectureReq);
    }

    //폐강
    @PostMapping(value = "/lectureClose", produces = "application/json; charset=UTF-8")
    public ResponseEntity<LectureDto> lectureClose(@Valid @RequestBody CloseReq closeReq) {
        logger.info("폐강할 강의의 강의명:: " + closeReq.getLectureName());
        return teacherService.lectureClose(closeReq);
    }

    //강사의 추천으로 등록한 회원의 정보
    @GetMapping(value = "/recommendedStudentList")
    public ResponseEntity<Page<StudentDto>> recommendedStudentList(int page, int size, String sort) {
        logger.info("강사의 추천을 받은 학생 리스트 조회");
        if(sort==null || sort.trim().isEmpty() || Integer.valueOf(page)==null || Integer.valueOf(size)==null || size <= 0){
            throw new IllegalStateException();
        }
        return teacherService.recommendedStudentList(page,size,sort);
    }

}
