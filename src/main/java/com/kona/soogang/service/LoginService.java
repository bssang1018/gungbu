package com.kona.soogang.service;

import com.kona.soogang.domain.StudentRepository;
import com.kona.soogang.domain.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final HttpServletRequest httpServletRequest;

    public String login(String id, String pw) {
        if (id.contains("@")){
            if(studentRepository.findById(id).get().getEmail().equals(id)) {
                System.out.println("### 학생으로 로그인 했습니다. ###");
                return "성공";
            }else{
                System.out.println("### 로그인을 실패 했습니다. ###");
            }

        }else{
            if(teacherRepository.findById(id).get().getId().equals(id)) {
                System.out.println("### 선생으로 로그인 했습니다. ###");
                return "성공";
            }else{
                System.out.println("### 로그인을 실패 했습니다. ###");
            }

        }
        return "실패";
    }

    public String currentId(){
        return (String) httpServletRequest.getSession().getAttribute("loginId");
    }
}
