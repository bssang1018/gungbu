package com.kona.soogang.dto;

import com.kona.soogang.domain.Student;
import com.kona.soogang.domain.Teacher;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class StudentDto {

    private Long studentNum;
    private String email;
    private String name;
    private String joinStatus;
    private Long teacherNum;

    @Builder
    public StudentDto(Long studentNum, String email, String name, String joinStatus, Long teacherNum){
        this.studentNum = studentNum;
        this.email = email;
        this.name = name;
        this.joinStatus = joinStatus;
        this.teacherNum = teacherNum;
    }

    public StudentDto(Student student){
        this.studentNum = student.getStudentNum();
        this.email = student.getEmail();
        this.name = student.getName();
        this.joinStatus = student.getJoinStatus();
        this.teacherNum = student.getTeacher().getTeacherNum();
    }

    //DTO를 Entity로 변환하자!
    public Student toEntity(){
        return Student.builder()
                .studentNum(studentNum)
                .email(email)
                .name(name)
                .joinStatus(joinStatus)
                .teacher(Teacher.builder().teacherNum(teacherNum).build())
                .build();
    }

}
