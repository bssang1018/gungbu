package com.kona.soogang.dto;

import com.kona.soogang.domain.Teacher;
import lombok.Getter;

@Getter
public class TeacherResDto {

    private String teacher_id;
    private String teacher_name;
    private String teacher_pw;

    public TeacherResDto(Teacher teacher){
        this.teacher_id = teacher.getTeacher_id();
        this.teacher_pw = teacher.getTeacher_name();
        this.teacher_name = teacher.getTeacher_pw();
    }
}
