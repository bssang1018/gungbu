package com.kona.soogang.controller;

import com.kona.soogang.service.TeacherService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RequestMapping("/teacher")
@RestController
@RequiredArgsConstructor
public class TeacherContorller {

    private final TeacherService teacherService;

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
    @GetMapping(value = "/recommend/{email}")
    public String recommend(@PathVariable String email, HttpSession session){
        System.out.println("추천할 이메일 :: " + email);
        return teacherService.recommend(email, session);
    }

}
