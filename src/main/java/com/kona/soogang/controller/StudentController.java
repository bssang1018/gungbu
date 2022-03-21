package com.kona.soogang.controller;

import com.kona.soogang.dto.RegisterDto;
import com.kona.soogang.dto.RegisterResponse;
import com.kona.soogang.dto.StudentDto;
import com.kona.soogang.service.StudentService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/student")
@RestController
public class StudentController {

    //@RequiredArgsConstructor 사용하지 않고, 직접 생성자 명시
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    //학생등록
    @PostMapping(value = "/join", produces = "application/json; charset=UTF-8")
    public String studentJoin(@RequestBody StudentDto studentDto) {
        System.out.println("회원가입 파라미터 :: " + studentDto.getEmail()+ " / "+ studentDto.getName());
        return studentService.studentJoin(studentDto);
    }

    //수강신청
    @PostMapping(value = "/lectureRegister", produces = "application/json; charset=UTF-8")
    public String lectureRegister(@RequestBody RegisterDto registerDto) {
        System.out.println("수강신청 파라미터 확인 :: " + registerDto.getLectureName() +" / "+registerDto.getEmail());
        return studentService.lectureRegister(registerDto);
    }

    //수강취소
    @PostMapping(value = "/registerCancel", produces = "application/json; charset=UTF-8")
    public String registerCancel(@RequestBody RegisterDto registerDto) {
        System.out.println("수강취소 파라미터 확인 :: "  + registerDto.getLectureName() +" / "+registerDto.getEmail());
        return studentService.registerCancel(registerDto);
    }

    //수강신청 조회
    @GetMapping(value = "/registerList")
    public List<RegisterResponse> registerList(@RequestParam String email, Pageable pageable) {
        return studentService.registerList(email, pageable);
    }

    //나를 추천한 강사의 이름 확인
    @GetMapping(value = "/recommendedMe/{email}")
    public String recommendedMe(@PathVariable String email) {
        System.out.println("추천 강사를 확인할 이메일:: " + email);
        return studentService.recommendedMe(email);
    }
}
