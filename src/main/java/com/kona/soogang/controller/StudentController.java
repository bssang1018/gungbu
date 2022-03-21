package com.kona.soogang.controller;

import com.kona.soogang.dto.RegisterDto;
import com.kona.soogang.dto.StudentDto;
import com.kona.soogang.service.StudentService;
import com.kona.soogang.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@RequestMapping("/student")
@RestController
public class StudentController {

    //@RequiredArgsConstructor 사용하지 않고, 직접 생성자 명시
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    //학생 가입
//    @GetMapping(value = "/join/{email}/{pw}/{name}")
//    public String studentJoin(@PathVariable String email, @PathVariable String pw, @PathVariable String name){
//        System.out.println("student join 파라미터 확인 :: " + email + " / " + pw + " / " + name);
//        return studentService.studentJoin(email, pw, name);
//    }

    //    @PostMapping(value = "/join", produces = "application/json; charset=UTF-8")
//    public String studentJoin(@RequestBody HashMap<String, String> param){
//        System.out.println("회원가입 파라미터 :: "+ param.toString());
//        return studentService.studentJoin(param);
//    }
    @PostMapping(value = "/join", produces = "application/json; charset=UTF-8")
    public String studentJoin(@RequestBody StudentDto studentDto) {
        System.out.println("회원가입 파라미터 :: " + studentDto.getEmail());
        return studentService.studentJoin(studentDto);
    }


    //    //학생 로그인
//    @GetMapping(value = "/login/{email}/{pw}")
//    public String studentLogin(@PathVariable String email, @PathVariable String pw, HttpSession session){
//        System.out.println("student login 파라미터 확인 :: " + email + " / " + pw);
//        return studentService.studentLogin(email, pw, session);
//    }
    //학생 로그인
    @PostMapping(value = "/login")
    public String studentLogin(@RequestBody StudentDto studentDto, HttpSession session) {
        System.out.println("student login 파라미터 확인 :: " + studentDto.getEmail());
        return studentService.studentLogin(studentDto, session);
    }

    //나를 추천한 강사의 이름 확인
    @GetMapping(value = "/recommendedMe/{email}")
    public StringBuffer recommendedMe(@PathVariable String email) {
        System.out.println("추천 강사를 확인할 이메일:: " + email);
        return studentService.recommendedMe(email);
    }

//    //수강신청
//    @GetMapping(value = "/lectureRegister/{lectureName}")
//    public String lectureRegister(@PathVariable String lectureName, HttpSession session){
//        System.out.println("수강신청 파라미터 확인 :: " + lectureName);
//        return studentService.lectureRegister(lectureName, session);
//    }

    //수강신청
    @PostMapping(value = "/lectureRegister", produces = "application/json; charset=UTF-8")
    public String lectureRegister(@RequestBody RegisterDto registerDto) {
        System.out.println("수강신청한 강의코드 :: " + registerDto.getLectureCode() + " / " + registerDto.getEmail());
        return studentService.lectureRegister(registerDto);
    }

//    //수강취소
//    @GetMapping(value = "/registerCancel/{lectureName}")
//    public String registerCancel(@PathVariable String lectureName, HttpSession session){
//        System.out.println("수강취소 파라미터 확인 :: " + lectureName);
//        return studentService.registerCancel(lectureName, session);
//    }

    //수강취소
    @PostMapping(value = "/registerCancel", produces = "application/json; charset=UTF-8")
    public String registerCancel(@RequestBody RegisterDto registerDto) {
        System.out.println("수강취소 파라미터 확인 :: " + registerDto.getLectureCode());
        return studentService.registerCancel(registerDto);
    }

    //수강신청 조회
    @GetMapping(value = "/registerList")
    public List<RegisterDto> registerList(HttpSession session) {
        return studentService.registerList(session);
    }


}
