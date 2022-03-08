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
public class StudentContorller {

    private final StudentService studentService;

    @GetMapping(value = "/join/{student_email}/{student_pw}/{student_name}", produces = "application/json; charset=UTF-8")
    public void studentJoin(@PathVariable String student_email, @PathVariable String student_pw, @PathVariable String student_name){
        System.out.println("student 파라미터 확인 :: " + student_email + " / " + student_pw + " / " + student_name);
        studentService.studentJoin(student_email, student_pw, student_name);
    }



}
