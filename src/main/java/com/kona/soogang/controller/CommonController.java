package com.kona.soogang.controller;

import com.kona.soogang.aop.LoginCheck;
import com.kona.soogang.domain.Lecture;
import com.kona.soogang.dto.LectureDto;
import com.kona.soogang.service.CommonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class CommonController {

    //@RequiredArgsConstructor 사용하지 않고, 직접 생성자 명시
    private final CommonService commonService;
    private final HttpServletRequest httpServletRequest;
    public CommonController(CommonService commonService, HttpServletRequest httpServletRequest){
        this.commonService = commonService;
        this.httpServletRequest = httpServletRequest;
    }

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
    //Pageable 사용해보기
    @GetMapping(value = "/lectureList")
    @ResponseBody
    public List<LectureDto> lectureList(Pageable pageable){
        return commonService.lectureList(pageable);
    }


}
