package com.kona.soogang.dto;

import com.kona.soogang.domain.Student;
import lombok.Getter;

@Getter
public class StudentResDto {

    private String email;
    private String name;
    private String pw;
    private String joinStatus;

    private String id;

    public StudentResDto(Student student){
        this.id = getId();

        this.email = student.getEmail();
        this.name = student.getName();
        this.pw = student.getPw();
        this.joinStatus = student.getJoinStatus();
    }

}
