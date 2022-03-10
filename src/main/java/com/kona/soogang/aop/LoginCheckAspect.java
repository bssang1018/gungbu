package com.kona.soogang.aop;

import com.kona.soogang.controller.CommonController;
import com.kona.soogang.service.LoginService;
import com.kona.soogang.service.TeacherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Aspect
@Component
@RequiredArgsConstructor
public class LoginCheckAspect {

    private final LoginService loginService;

    @Before("@annotation(LoginCheck)")
    public void loginCheck() throws HttpClientErrorException{

        String currentId = loginService.currentId();

        if (currentId == null){
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        }

    }
}
