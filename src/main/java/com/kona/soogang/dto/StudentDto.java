package com.kona.soogang.dto;

import com.kona.soogang.domain.Student;
import com.kona.soogang.domain.Teacher;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StudentDto {

    private String email;
    private String pw;
    private String name;
    private String joinStatus;
    private String id;

    @Builder
    public StudentDto(String email, String pw, String name, String joinStatus, String id){
        this.email = email;
        this.pw = pw;
        this.name = name;
        this.joinStatus = joinStatus;
        this.id = id;
    }

    public StudentDto(Student student){
        this.email = student.getEmail();
        this.pw = student.getPw();
        this.name = student.getName();
        this.joinStatus = getJoinStatus();
        this.id = student.getTeacher().getId();
    }

    //Dto를 Entity로 바꿔서 DB에 삽입하기 위함.
    public Student toEntity(){
        return Student.builder()
                .email(email)
                .pw(pw)
                .name(name)
                .joinStatus(joinStatus)
                .teacher(Teacher.builder().id(id).build())
                .build();
    }

}
