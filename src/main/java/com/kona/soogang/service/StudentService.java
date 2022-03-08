package com.kona.soogang.service;

import com.kona.soogang.domain.Student;
import com.kona.soogang.domain.StudentRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    //학생 회원 등록
    public void studentJoin(String student_email, String student_pw, String student_name) {

        Student student = new Student();
        student.setStudent_email(student_email);
        student.setStudent_pw(student_pw);
        student.setStudent_name(student_name);

        //강사의 추천을 확인
        if (recomendCheck(student_email).equals("추천")) {
            student.setJoin_status("BY");
            //BY : 사전에 추천받았고, 현재 가입한 상태
        }else{
            student.setJoin_status("NO");
            //NO : 추천을 받지 못한 상태
        }

        studentRepository.save(student);
    }

    //학생 추천 여부 체크
    public String recomendCheck(String student_email){

        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        System.out.println(studentRepository.findById(student_email));
        studentRepository.findById(student_email).getClass();
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

        Optional<Student> student = studentRepository.findById(student_email);

        if(student.isPresent() && !student.get().getStudent_email().equals(null)) {
            System.out.println("### 강사의 추천을 받은 이메일 입니다. ###");
            return "추천";
        }else{
            System.out.println("### 추천 기록이 없습니다. 일반회원으로 가입 됩니다. ###");
            return "미추천";
        }
    }
}
