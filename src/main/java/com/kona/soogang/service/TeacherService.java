package com.kona.soogang.service;

import com.kona.soogang.domain.*;
import com.kona.soogang.dto.StudentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final LectureRepository lectureRepository;

    // 강사 회원가입
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

    @Transactional
    public String lectureInsert(String lectureName, int maxPerson, HttpSession session){

        if (maxPerson > 100){
            throw new IllegalStateException("INFO:: the maximum number of participants is 100.");
        }

        validateDuplicateLecture(lectureName);

        String teacherId = (String) session.getAttribute("loginId");

        Teacher teacher = new Teacher();
        teacher.setId(teacherId);
        Lecture lecture = new Lecture();

        lecture.setTeacher(teacher);
        lecture.setLectureName(lectureName);
        lecture.setMaxPerson(maxPerson);
        lecture.setCloseStatus("NO");

        lectureRepository.save(lecture);

        String lectureInsertMent = '"' + lectureName + '"' + "강의가 등록됐습니다.";
        return lectureInsertMent;
    }

    private void validateDuplicateLecture(String lectureName){
        List<Lecture> LectureDuplicateResult = lectureRepository.findByLectureName(lectureName);
        if (!LectureDuplicateResult.isEmpty()){ //엠프티 하지 않다면 중복 검색 결과가 있으니까, 예외
            throw new IllegalStateException("INFO :: already exist lecture name");
        }
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


    @Transactional
    public String recommend(String email, HttpSession session) {

        String teacherId = (String) session.getAttribute("loginId");
        System.out.println("강사아이디 확인용 :: " + teacherId);
        String recommendMent = "";

        // 등록하려는 이메일이 테이블에 있는지 확인하기 위함
        Optional<Student> recommendResult = studentRepository.findById(email);

        Teacher teacher = new Teacher();
        teacher.setId(teacherId);
        Student student = new Student();
        student.setTeacher(teacher);

        if(!recommendResult.isPresent()){ // 이메일이 없으면 사전추천, 미가입 상태로 BN => INSERT
            student.setEmail(email);
            student.setJoinStatus("BN");
            student.setTeacher(teacher);
            studentRepository.save(student);

            recommendMent = email + " 을 추천하고 새로운 계정으로 등록했습니다.";
            return recommendMent;
        }

        if(recommendResult.isPresent() && recommendResult.get().getJoinStatus().equals("NO")) { //강사아이디랑, 상태만 => UPDATE
            studentRepository.recommendUpdate("AY", teacherId, email);
            recommendMent = email + " 을 추천했습니다.";
            return recommendMent;
        }else{
            throw new IllegalStateException("INFO :: that email was already recommended");
        }

    }

    public List<StudentDto> recommendedStudentList(HttpSession session) {
        String email = (String) session.getAttribute("loginId");

        List<Student> studentList = studentRepository.recommendedStudentList();

        return studentList.stream().map(StudentDto::new).collect(Collectors.toList());
    }
}



