package com.kona.soogang.dto;


import com.kona.soogang.domain.Student;
import com.kona.soogang.domain.Teacher;
import com.kona.soogang.domain.TeacherRepository;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StudentReqDto {

    private String student_email;
    private String student_name;
    private String student_pw;
    private String join_status;

    private String teacher_id;

    @Builder
    public StudentReqDto(String teacher_id, String student_email, String student_name, String student_pw, String join_status){
        this.teacher_id = teacher_id;

        this.student_email = student_email;
        this.student_name = student_name;
        this.student_pw = student_pw;
        this.join_status = join_status;
    }

}
