package com.kona.soogang.controller;

import com.kona.soogang.service.StudentService;
import com.kona.soogang.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RequestMapping("/student")
@RestController
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    //학생 가입
    @GetMapping(value = "/join/{email}/{pw}/{name}")
    public String studentJoin(@PathVariable String email, @PathVariable String pw, @PathVariable String name){
        System.out.println("student join 파라미터 확인 :: " + email + " / " + pw + " / " + name);
        return studentService.studentJoin(email, pw, name);
    }

    //학생 로그인
    @GetMapping(value = "/login/{email}/{pw}")
    public String studentLogin(@PathVariable String email, @PathVariable String pw, HttpSession session){
        System.out.println("student join 파라미터 확인 :: " + email + " / " + pw);
        return studentService.studentLogin(email, pw, session);
    }

    //나를 추천한 강사의 이름 확인
    @GetMapping(value = "/recommendedMe")
    public StringBuffer recommendedMe(HttpSession session){
        return studentService.recommendedMe(session);
    }

    //수강신청
    @GetMapping(value = "/lectureRegister/{lectureName}")
    public String lectureRegister(@PathVariable String lectureName, HttpSession session){
        System.out.println("수강신청 파라미터 확인 :: " + lectureName);
        return studentService.lectureRegister(lectureName, session);
    }

    //수강취소
    @GetMapping(value = "/registerCancel/{lectureName}")
    public String registerCancel(@PathVariable String lectureName, HttpSession session){
        System.out.println("수강취소 파라미터 확인 :: " + lectureName);
        return studentService.registerCancel(lectureName, session);
    }

    //수강신청 조회


}
