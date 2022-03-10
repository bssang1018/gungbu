package com.kona.soogang.controller;

import com.kona.soogang.service.StudentService;
import com.kona.soogang.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/student")
@RestController
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    //학생 가입
    @GetMapping(value = "/join/{email}/{pw}/{name}")
    public void studentJoin(@PathVariable String email, @PathVariable String pw, @PathVariable String name){
        System.out.println("student 파라미터 확인 :: " + email + " / " + pw + " / " + name);
        studentService.studentJoin(email, pw, name);
    }



}
