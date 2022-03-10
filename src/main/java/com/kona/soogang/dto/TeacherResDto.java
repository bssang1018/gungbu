package com.kona.soogang.dto;

import com.kona.soogang.domain.Teacher;
import lombok.Getter;

@Getter
public class TeacherResDto {

    private String id;
    private String name;
    private String pw;

    public TeacherResDto(Teacher teacher){
        this.id = teacher.getId();
        this.pw = teacher.getPw();
        this.name = teacher.getName();
    }
}
