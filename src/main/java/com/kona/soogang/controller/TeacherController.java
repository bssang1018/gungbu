package com.kona.soogang.controller;

import com.kona.soogang.aop.LoginCheck;
import com.kona.soogang.domain.Teacher;
import com.kona.soogang.dto.LectureDto;
import com.kona.soogang.dto.StudentDto;
import com.kona.soogang.dto.TeacherDto;
import com.kona.soogang.service.TeacherService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.apache.logging.log4j.message.LoggerNameAwareMessage;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@RequestMapping("/teacher")
@RestController
public class TeacherController {

    //@RequiredArgsConstructor 사용하지 않고, 직접 생성자 명시
    private final TeacherService teacherService;
    public TeacherController(TeacherService teacherService){
        this.teacherService = teacherService;
    }

    //폐강
    @PostMapping(value = "/lectureClose", produces = "application/json; charset=UTF-8")
    public String lectureClose(@RequestBody LectureDto lectureDto) {
        System.out.println("폐강할 강의 코드:: " + lectureDto.getLectureName());
        return teacherService.lectureClose(lectureDto);
    }

//    //강사 가입
//    @GetMapping(value = "/join/{id}/{pw}/{name}")
//    public String teacherJoin(@PathVariable String id, @PathVariable String pw, @PathVariable String name) {
//        System.out.println("teacher join 파라미터 확인 :: " + id + " / " + pw + " / " + name);
//        return teacherService.teacherJoin(id, pw, name);
//    }

    //강사 가입
    @PostMapping(value = "/join", produces = "application/json; charset=UTF-8")
    public String teacherJoin(@RequestBody TeacherDto teacherDto) {
        System.out.println("teacher join 파라미터 확인 :: " + teacherDto.getId());
        return teacherService.teacherJoin(teacherDto);
    }

    //    //강사 로그인
//    @GetMapping(value= "/login/{id}/{pw}")
//    public String teacherLogin(@PathVariable String id, @PathVariable String pw, HttpSession session){
//        System.out.println("teacher login 파라미터 확인 :: " + id + " / " + pw);http://localhost:8080/teacher/join
//        return teacherService.teacherLogin(id, pw, session);
//    }
    //강사 로그인
    @PostMapping(value = "/login", produces = "application/json; charset=UTF-8")
    public String teacherLogin(@RequestBody TeacherDto teacherDto, HttpSession session) {
        System.out.println("teacher login 파라미터 확인 :: " + teacherDto.getId());
        return teacherService.teacherLogin(teacherDto, session);
    }

    //    //학생 추천
//    @GetMapping(value = "/recommend/{email}")
//    public String recommend(@PathVariable String email, HttpSession session){
//        System.out.println("추천할 이메일 :: " + email);
//        return teacherService.recommend(email, session);
//    }
//    //학생 추천
//    @PostMapping(value = "/recommend")
//    public String recommend(@RequestBody HashMap<String, String> param) {
//        System.out.println("추천할 이메일 :: " + param.toString());
//        return teacherService.recommend(param);
//    }
    //학생 추천
    @PostMapping(value = "/recommend", produces = "application/json; charset=UTF-8")
    public String recommend(@RequestBody StudentDto studentDto) {
        System.out.println("추천할 이메일 :: " + studentDto.getEmail());
        return teacherService.recommend(studentDto);
    }


//    //강의 등록
//    @GetMapping(value = "/lectureInsert/{lectureName}/{maxPerson}")
//    public String lectureInsert(@PathVariable String lectureName, @PathVariable int maxPerson, HttpSession session) {
//        System.out.println("등록할 강의명 :: " + lectureName + " / " + maxPerson);
//        return teacherService.lectureInsert(lectureName, maxPerson, session);
//    }
    //강의 등록
    @PostMapping(value = "/lectureInsert", produces = "application/json; charset=UTF-8")
    public String lectureInsert(@RequestBody LectureDto lectureDto) {
        System.out.println("등록할 강의명 :: " + lectureDto.getLectureName() + " / " + lectureDto.getId());
        return teacherService.lectureInsert(lectureDto);
    }

    //강사의 추천으로 등록한 회원의 정보
    @GetMapping(value = "/recommendedStudentList")
    public List<StudentDto> recommendedStudentList() {
        return teacherService.recommendedStudentList();
    }

}
