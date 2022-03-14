package com.kona.soogang.dto;

import com.kona.soogang.domain.Student;
import lombok.Data;

@Data
public class StudentDto {

    private String email;
    private String name;
    private String joinStatus;

    public StudentDto(Student student){
        this.email = student.getEmail();
        this.name = student.getName();
        this.joinStatus = student.getJoinStatus();
    }

}
