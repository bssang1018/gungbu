package com.kona.soogang.controller;

import com.kona.soogang.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TeacherContorller {

    private final TeacherService teacherService;

    @GetMapping("/")
    public String hello(){
        return teacherService.serviceHello();
    }

    //강사 가입
    @GetMapping(value = "/join/{teacher_id}/{teacher_pw}/{teacher_name}", produces = "application/json; charset=UTF-8")
    public void teacherJoin(@PathVariable String teacher_id, @PathVariable String teacher_pw, @PathVariable String teacher_name){
        System.out.println("teacher join 파라미터 확인 :: " + teacher_id + " / " + teacher_pw + " / " + teacher_name);
        teacherService.teacherJoin(teacher_id, teacher_pw, teacher_name);
    }

    //학생 추천
    @GetMapping(value = "/recomend/{teacher_id}/{student_id}", produces = "application/json; charset=UTF-8")
    public void studentRecomend(@PathVariable String teacher_id, @PathVariable String student_id){
        System.out.println("teacher recomend 파라미터 확인 :: " + teacher_id + " / " + student_id);
    }

}
