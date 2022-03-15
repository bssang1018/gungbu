package com.kona.soogang.controller;

import com.kona.soogang.aop.LoginCheck;
import com.kona.soogang.dto.StudentDto;
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
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    //폐강
    @PostMapping(value = "/lectureClose", produces = "application/json; charset=UTF-8")
    public String lectureClose(@RequestBody HashMap<String, Long> param){
        System.out.println("폐강할 강의 코드:: " + param.get("lectureCode"));
        return teacherService.lectureClose(param);
    }

    //강사 가입
    @GetMapping(value = "/join/{id}/{pw}/{name}")
    public String teacherJoin(@PathVariable String id, @PathVariable String pw, @PathVariable String name) {
        System.out.println("teacher join 파라미터 확인 :: " + id + " / " + pw + " / " + name);
        return teacherService.teacherJoin(id, pw, name);
    }

    //강사 로그인
    @GetMapping(value= "/login/{id}/{pw}")
    public String teacherLogin(@PathVariable String id, @PathVariable String pw, HttpSession session){
        System.out.println("teacher login 파라미터 확인 :: " + id + " / " + pw);
        return teacherService.teacherLogin(id, pw, session);
    }

    //학생 추천
    @LoginCheck
    @GetMapping(value = "/recommend/{email}")
    public String recommend(@PathVariable String email, HttpSession session){
        System.out.println("추천할 이메일 :: " + email);
        return teacherService.recommend(email, session);
    }

    //강의 등록
    @LoginCheck
    @GetMapping(value = "lectureInsert/{lectureName}/{maxPerson}")
    public String lectureInsert(@PathVariable String lectureName, @PathVariable int maxPerson, HttpSession session){
        System.out.println("등록할 강의명 :: " + lectureName + " / " + maxPerson);
        return teacherService.lectureInsert(lectureName, maxPerson, session);
    }

    //강사의 추천으로 등록한 회원의 정보
    @LoginCheck
    @GetMapping(value = "/recommendedStudentList")
    public List<StudentDto> recommendedStudentList(HttpSession session){
        return teacherService.recommendedStudentList(session);
    }

}