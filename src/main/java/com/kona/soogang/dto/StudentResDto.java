package com.kona.soogang.dto;

import com.kona.soogang.domain.Student;
import lombok.Getter;

@Getter
public class StudentResDto {

    private String student_email;
    private String student_name;
    private String student_pw;
    private String join_status;

    private String teacher_id;

    public StudentResDto(Student student){
        this.teacher_id = getTeacher_id();

        this.student_email = student.getStudent_email();
        this.student_name = student.getStudent_name();
        this.student_pw = student.getStudent_pw();
        this.join_status = student.getJoin_status();
    }

}
