package com.kona.soogang.controller;

import com.kona.soogang.aop.LoginCheck;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
public class CommonController {

    @GetMapping(value = "/")
    public String firstPage(){
        return "안녕하세요! 만나서 반갑습니다.";
    }

    @LoginCheck //aop 로그인 상태 체크
    @GetMapping(value = "/logout")
    public String logout(HttpSession session){
        String nowLoginId = (String) session.getAttribute("loginId");
        System.out.println("로그아웃 할 아이디 :: " + nowLoginId);
        session.removeAttribute("loginId");
        return "로그아웃 되었습니다.";
    }
}
