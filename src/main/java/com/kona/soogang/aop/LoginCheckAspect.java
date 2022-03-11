package com.kona.soogang.aop;

import com.kona.soogang.controller.CommonController;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

@Aspect
@Component
@RequiredArgsConstructor
public class LoginCheckAspect {

    private final CommonController commonController;

    @Before("@annotation(LoginCheck)")
    public void loginCheck() throws HttpClientErrorException{

        String currentId = commonController.currentId();
        System.out.println("currentId 값 확인 :: " + currentId);

        if (currentId == null){
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        }

    }

}
