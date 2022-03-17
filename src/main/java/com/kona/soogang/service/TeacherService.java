package com.kona.soogang.service;

import com.kona.soogang.domain.*;
import com.kona.soogang.dto.LectureDto;
import com.kona.soogang.dto.StudentDto;
import com.kona.soogang.dto.TeacherDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final LectureRepository lectureRepository;
    private final RegisterRepository registerRepository;
    public TeacherService(TeacherRepository teacherRepository, StudentRepository studentRepository, LectureRepository lectureRepository, RegisterRepository registerRepository) {
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.lectureRepository = lectureRepository;
        this.registerRepository = registerRepository;
    }

    //    // 강사 회원가입
//    @Transactional
//    public String teacherJoin(String id, String pw, String name) {
//        Teacher teacher = new Teacher();
//        teacher.setId(id);
//        teacher.setPw(pw);
//        teacher.setName(name);
//
//        // id 중복검사
//        validateDuplicate(teacher);
//
//        teacherRepository.save(teacher);
//
//        String joinMent = id + "님! 가입이 되었습니다.";
//        return joinMent;
//    }
    // 강사 회원가입
    @Transactional(rollbackFor = Exception.class)
    public String teacherJoin(@RequestBody TeacherDto teacherDto) {

        Teacher teacher = teacherDto.toEntity();

        // id 중복검사
        validateDuplicate(teacher);

        teacherRepository.save(teacher);

        return teacherDto.getId() + "님! 가입이 되었습니다.";
    }

    //    @Transactional
//    public String lectureInsert(String lectureName, int maxPerson, HttpSession session) {
//
//        if (maxPerson > 100) {
//            throw new IllegalStateException("INFO:: the maximum number of participants is 100.");
//        }
//
//        validateDuplicateLecture(lectureName);
//
//        String teacherId = (String) session.getAttribute("loginId");
//
//        Teacher teacher = new Teacher();
//        teacher.setId(teacherId);
//        Lecture lecture = new Lecture();
//
//        lecture.setTeacher(teacher);
//        lecture.setLectureName(lectureName);
//        lecture.setMaxPerson(maxPerson);
//        lecture.setCloseStatus("NO");
//
//        lectureRepository.save(lecture);
//
//        String lectureInsertMent = '"' + lectureName + '"' + "강의가 등록됐습니다.";
//        return lectureInsertMent;
//    }
    @Transactional(rollbackFor = Exception.class)
    public String lectureInsert(LectureDto lectureDto) {

        if (lectureDto.getMaxPerson() > 100) {
            throw new IllegalStateException("INFO:: the maximum number of participants is 100.");
        }
        validateDuplicateLecture(lectureDto.getLectureCode());

        lectureDto.setId(lectureDto.getId());
        lectureDto.setCloseStatus("NO");
        lectureRepository.save(lectureDto.toEntity());

        return "강의를 등록했습니다.(강의명: "+lectureDto.getLectureName()+")";
    }

    private void validateDuplicateLecture(long lectureCode) {
        List<Lecture> LectureDuplicateResult = lectureRepository.findByLectureName(lectureCode);
        if (!LectureDuplicateResult.isEmpty()) { //엠프티 하지 않다면 중복 검색 결과가 있으니까, 예외
            throw new IllegalStateException("INFO :: already exist lecture name");
        }
    }

    // id 중복검사 메서드
    private void validateDuplicate(Teacher teacher) {
        Optional<Teacher> duplicateResult = teacherRepository.findById(teacher.getId());
        if (duplicateResult.isPresent()) { //true면 중복
            throw new IllegalStateException("INFO :: already exist Id!!"); // 예외 던지기
        }
    }

    //    public String teacherLogin(String id, String pw, HttpSession session) {
//        List<Teacher> loginResult = teacherRepository.findByIdAndPw(id, pw);
//
//        if (loginResult.isEmpty()) {
//            throw new IllegalStateException("INFO :: login fail!");
//        }
//
//        session.setAttribute("loginId", id);
//
//        String sessionCheck = (String) session.getAttribute("loginId");
//        System.out.println("세션에 값 정상적으로 들어갔는지 확인:: " + sessionCheck);
//
//        String loginMent = id + "님! 로그인 됐습니다!";
//        return loginMent;
//    }
    public String teacherLogin(TeacherDto teacherDto, HttpSession session) {
        List<Teacher> loginResult = teacherRepository.findByIdAndPw(teacherDto.getId(), teacherDto.getPw());

        if (loginResult.isEmpty()) {
            throw new IllegalStateException("INFO :: login fail!");
        }

        session.setAttribute("loginId", teacherDto.getId());

        return teacherDto.getId() + "님! 로그인 됐습니다!";
    }

    //    @Transactional
//    public String recommend(String email, HttpSession session) {
//
//        String teacherId = (String) session.getAttribute("loginId");
//        System.out.println("강사아이디 확인용 :: " + teacherId);
//        String recommendMent = "";
//
//        // 등록하려는 이메일이 테이블에 있는지 확인하기 위함
//        Optional<Student> recommendResult = studentRepository.findById(email);
//
//        Teacher teacher = new Teacher();
//        teacher.setId(teacherId);
//        Student student = new Student();
//        student.setTeacher(teacher);
//
//        if (!recommendResult.isPresent()) { // 이메일이 없으면 사전추천, 미가입 상태로 BN => INSERT
//            student.setEmail(email);
//            student.setJoinStatus("BN");
//            student.setTeacher(teacher);
//            studentRepository.save(student);
//
//            recommendMent = email + " 을 추천하고 새로운 계정으로 등록했습니다.";
//            return recommendMent;
//        }
//
//        if (recommendResult.isPresent() && recommendResult.get().getJoinStatus().equals("NO")) { //강사아이디랑, 상태만 => UPDATE
//            studentRepository.recommendUpdate("AY", teacherId, email);
//            recommendMent = email + " 을 추천했습니다.";
//            return recommendMent;
//        } else {
//            throw new IllegalStateException("INFO :: that email was already recommended");
//        }
//
//    }
//    @Transactional(rollbackFor = Exception.class)
//    public String recommend(HashMap<String, String> param) {
//
//        String teacherId = param.get("loginId");
//        String email = param.get("studentEmail");
//        System.out.println("강사아이디 확인용 :: " + teacherId);
//
//        String recommendMent = "";
//
//        // 등록하려는 이메일이 테이블에 있는지 확인하기 위함
//        Optional<Student> recommendResult = studentRepository.findById(email);
//
//        Teacher teacher = new Teacher();
//        teacher.setId(teacherId);
//        Student student = new Student();
//        student.setTeacher(teacher);
//
//        if (!recommendResult.isPresent()) { // 이메일이 없으면 사전추천, 미가입 상태로 BN => INSERT
//            student.setEmail(email);
//            student.setJoinStatus("BN");
//            student.setTeacher(teacher);
//            studentRepository.save(student);
//
//            recommendMent = email + " 을 추천하고 새로운 계정으로 등록했습니다.";
//            return recommendMent;
//        }
//
//        if (recommendResult.isPresent() && recommendResult.get().getJoinStatus().equals("NO")) { //강사아이디랑, 상태만 => UPDATE
//            studentRepository.recommendUpdate("AY", teacherId, email);
//            recommendMent = email + " 을 추천했습니다.";
//            return recommendMent;
//        } else {
//            throw new IllegalStateException("INFO :: that email was already recommended");
//        }
//    }
    @Transactional(rollbackFor = Exception.class)
    public String recommend(StudentDto studentDto) {

        String teacherId = studentDto.getId();
        String email = studentDto.getEmail();
        System.out.println("강사아이디 확인용 :: " + teacherId);

        String recommendMent = "";

        // 등록하려는 이메일이 테이블에 있는지 확인하기 위함
        Optional<Student> recommendResult = studentRepository.findById(email);


        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setId(teacherId);
        Teacher teacher = teacherDto.toEntity();

        studentDto.setId(teacher.getId());

        if (!recommendResult.isPresent()) { // 이메일이 없으면 사전추천, 미가입 상태로 BN => INSERT
            studentDto.setEmail(email);
            studentDto.setJoinStatus("BN");
            studentDto.setId(teacherId);
            studentRepository.save(studentDto.toEntity());

            recommendMent = email + " 을 추천하고 새로운 계정으로 등록했습니다.";
            return recommendMent;
        }

        if (recommendResult.isPresent() && recommendResult.get().getJoinStatus().equals("NO")) { //강사아이디랑, 상태만 => UPDATE
            studentRepository.recommendUpdate("AY", teacherId, email);
            recommendMent = email + " 을 추천했습니다.";
            return recommendMent;
        } else {
            throw new IllegalStateException("INFO :: that email was already recommended");
        }
    }

    public List<StudentDto> recommendedStudentList() {
        List<Student> studentList = studentRepository.recommendedStudentList();
        return studentList.stream().map(StudentDto::new).collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class)
    public String lectureClose(LectureDto lectureDto) {
        int countRegistered = registerRepository.countRegistered(lectureDto.getLectureCode());

        if (countRegistered > 2) { //3명 부터 폐강 불가능
            throw new IllegalStateException("INFO:: you can't close the lecture");
        }

        if (lectureRepository.findById(lectureDto.getLectureCode()).get().getCloseStatus().equals("YES")) {
            throw new IllegalStateException("INFO:: already closed lecture");
        }

        lectureRepository.lectureClose(lectureDto.getLectureCode());

        return "폐강 했습니다.(강의코드: " + lectureDto.getLectureCode() + ")";
    }
}



