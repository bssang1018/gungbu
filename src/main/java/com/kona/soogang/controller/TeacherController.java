package com.kona.soogang.controller;

import com.kona.soogang.dto.LectureDto;
import com.kona.soogang.dto.StudentDto;
import com.kona.soogang.dto.TeacherDto;
import com.kona.soogang.service.TeacherService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/teacher")
@RestController
public class TeacherController {

    //@RequiredArgsConstructor 사용하지 않고, 직접 생성자 명시
    private final TeacherService teacherService;
    public TeacherController(TeacherService teacherService){
        this.teacherService = teacherService;
    }

    //강사 가입
    @PostMapping(value = "/join", produces = "application/json; charset=UTF-8")
    public String teacherJoin(@RequestBody TeacherDto teacherDto) {
        System.out.println("teacher join 파라미터 확인 :: " + teacherDto.getTeacherId());
        return teacherService.teacherJoin(teacherDto);
    }

    //학생 추천
    @PostMapping(value = "/recommend", produces = "application/json; charset=UTF-8")
    public String recommend(@RequestBody StudentDto studentDto) {
        System.out.println("추천할 이메일 :: " + studentDto.getEmail());
        return teacherService.recommend(studentDto);
    }

    //강의 등록
    @PostMapping(value = "/lectureInsert", produces = "application/json; charset=UTF-8")
    public String lectureInsert(@RequestBody LectureDto lectureDto) {
        System.out.println("강의 등록 파라미터 확인 :: " + lectureDto.getLectureName() + " / " + lectureDto.getMaxPerson() + " / " + lectureDto.getTeacherNum());
        return teacherService.lectureInsert(lectureDto);
    }

    //폐강
    @PostMapping(value = "/lectureClose", produces = "application/json; charset=UTF-8")
    public String lectureClose(@RequestBody LectureDto lectureDto) {
        System.out.println("폐강할 강의의 강의명:: " + lectureDto.getLectureName());
        return teacherService.lectureClose(lectureDto);
    }

    //강사의 추천으로 등록한 회원의 정보
    @GetMapping(value = "/recommendedStudentList")
    public List<StudentDto> recommendedStudentList(Pageable pageable) {
        System.out.println("강사의 추천을 받은 학생 리스트 조회");
        return teacherService.recommendedStudentList(pageable);
    }

}
