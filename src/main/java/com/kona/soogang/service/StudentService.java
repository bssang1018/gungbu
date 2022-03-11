package com.kona.soogang.service;

import com.kona.soogang.domain.*;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final LectureRepository lectureRepository;
    private final RegisterRepository registerRepository;

    //학생 회원가입
    @Transactional
    public String studentJoin(String email, String pw, String name) {

        Student student = new Student();
        student.setEmail(email);
        student.setPw(pw);
        student.setName(name);

        //이메일 중복 체크
        validateDuplicateCheck(email);

        //강사의 추천을 확인
        String recommendResult = recommendCheck(email);
        System.out.println("추천여부 확인 결과 :: "+recommendResult);

        student.setJoinStatus(recommendResult);

        studentRepository.save(student);

        String joinMent = email + "님! 가입이 되었습니다.";
        return joinMent;
    }

    //이메일 중복 체크
    public void validateDuplicateCheck(String email){
        Optional<Student> duplicateResult = studentRepository.findById(email);

        if (duplicateResult.isPresent() && !duplicateResult.get().getJoinStatus().equals("BN")) { //조회 결과가 유효하면서 AY,NO,BY 면 예외. 중복 이메일이라는 뜻
            throw new IllegalStateException("INFO :: already exist email");
        }
    }

    //학생 추천 여부 체크
    public String recommendCheck(String email){

        Optional<Student> studentOptional = studentRepository.findById(email);

        if (!studentOptional.isPresent()) { //중복없으면 따질것도 없이 신규 가입

            return "NO";
        }

        Student student = studentOptional.get();

        if (student.getEmail()==email && student.getJoinStatus()=="BN"){ //BN은 사전에 추천받았지만, 미가입상태
            return "BY"; //가입 상태로 업데이트
        }else{
            return "NO"; //사전 추천 받지 못한경우, NO
        }
    }

    public String studentLogin(String email, String pw, HttpSession session) {
        List<Student> loginResult = studentRepository.findByEmailAndPw(email, pw);
        if (loginResult.isEmpty()){
            throw new IllegalStateException("INFO :: login fail!");
        }

        session.setAttribute("loginId", email);

        String sessionCheck = (String) session.getAttribute("loginEmail");
        System.out.println("세션에 값 정상적으로 들어갔는지 확인:: " + sessionCheck);

        String loginMent = email + "님! 로그인 됐습니다!";
        return loginMent;

    }

    public StringBuffer recommendedMe(HttpSession session) {
        String email = (String) session.getAttribute("loginId");
        Optional<Student> result = studentRepository.findById(email);

        StringBuffer sb = new StringBuffer();

        if(!result.isPresent()){ //조회한 결과 없다면
            sb.append("추천을 받지 못했습니다.");
            return sb;
        }

        sb.append("나를 추천한 강사의 아이디는 ");
        sb.append(result.get().getTeacher().getId());
        sb.append(" 입니다.\r\n");
        sb.append("강사의 이름은 ");
        sb.append(result.get().getTeacher().getName());
        sb.append(" 입니다.");
        return sb;
    }


    public String lectureRegister(String lectureName,HttpSession session) {
        String email = (String) session.getAttribute("loginId");
        Optional<Lecture> lecture = lectureRepository.findByLectureName(lectureName);

        //인원수 체크하면서, 신청가능한지 부터.
        //int maxPerson = lecture.get().getMaxPerson();

        //중복신청 체크

        //추천 여부에 따라, 인원수 상관없이 신청 가능

        //수강 신청 등록
        RegisterId registerId = new RegisterId(lectureName, email);
        Register register = new Register();
        register.setRegisterId(registerId);
        register.setCancelStatus("NO");
        registerRepository.save(register);

        return "으악";
    }
}
