package com.kona.soogang.service;

import com.kona.soogang.domain.Student;
import com.kona.soogang.domain.Teacher;
import com.kona.soogang.domain.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final HttpServletRequest httpServletRequest;

    // 교사 회원가입
    @Transactional
    public String teacherJoin(String id, String pw, String name) {
        Teacher teacher = new Teacher();
        teacher.setId(id);
        teacher.setPw(pw);
        teacher.setName(name);

        // id 중복검사
        validateDuplicate(teacher);

        teacherRepository.save(teacher);

        String joinMent = id + "님! 가입이 되었습니다.";
        return joinMent;
    }

    // id 중복검사 메서드
    private void validateDuplicate(Teacher teacher){
        Optional<Teacher> duplicateResult = teacherRepository.findById(teacher.getId());
        if (duplicateResult.isPresent()){ //true면 중복
            throw new IllegalStateException("INFO :: already exist Id!!"); // 예외 던지기
        }
    }

    public String teacherLogin(String id, String pw, HttpSession session) {
        List<Teacher> loginResult = teacherRepository.findByIdAndPw(id,pw);

        if (loginResult.isEmpty()){
            throw new IllegalStateException("INFO :: login fail!");
        }

        session.setAttribute("loginId", id);

        String sessionCheck = (String) session.getAttribute("loginId");
        System.out.println("세션에 값 정상적으로 들어갔는지 확인:: " + sessionCheck);

        String loginMent = id + "님! 로그인 됐습니다!";
        return loginMent;
    }

    public String currentId(){
        return (String) httpServletRequest.getSession().getAttribute("loginId");
    }
}
