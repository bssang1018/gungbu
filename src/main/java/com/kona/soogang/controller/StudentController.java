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

}
