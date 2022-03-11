package com.kona.soogang.controller;

import com.kona.soogang.aop.LoginCheck;
import com.kona.soogang.domain.Lecture;
import com.kona.soogang.dto.LectureDto;
import com.kona.soogang.service.CommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommonController {

    private final CommonService commonService;
    // 세션
    private final HttpServletRequest httpServletRequest;

    // 세션에서 아이디 가져오기
    public String currentId(){
        String loginId = (String) httpServletRequest.getSession().getAttribute("loginId");
        System.out.println("loginId :: " + loginId);
        return loginId;
    }

    //첫페이지
    @GetMapping(value = "/")
    public String firstPage(){
        return "안녕하세요! 만나서 반갑습니다.";
    }

    //로그아웃
    @LoginCheck //aop 로그인 상태 체크
    @GetMapping(value = "/logout")
    public String logout(HttpSession session){
        String nowLoginId = (String) session.getAttribute("loginId");
        System.out.println("로그아웃 할 아이디 :: " + nowLoginId);
        session.removeAttribute("loginId");
        return "로그아웃 되었습니다.";
    }

    //강의 조회
    @LoginCheck
    @GetMapping(value = "/lectureList")
    public List<LectureDto> lectureList(){
        return commonService.lectureList();
    }


}
