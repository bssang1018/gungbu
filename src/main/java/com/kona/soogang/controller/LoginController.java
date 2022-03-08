package com.kona.soogang.controller;

import com.kona.soogang.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RequestMapping("/login/{id}/{pw}")
@RestController
public class LoginController {

    private final LoginService loginService;

    public String login(HttpSession session, @PathVariable String id, @PathVariable String pw){
        System.out.println("login 시도 :: " + id + " / " + pw);
        String result = loginService.login(id, pw);
        if (result.equals("성공")){
            session.setAttribute("loginId", id);
            return "로그인 성공";
        }
        return "로그인 실패";
    }
}
