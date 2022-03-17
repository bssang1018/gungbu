package com.kona.soogang.dto;

import com.kona.soogang.domain.Teacher;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TeacherDto {

    private String id;
    private String pw;
    private String name;

    @Builder
    public TeacherDto(String id, String pw, String name){
        this.id = id;
        this.pw = pw;
        this.name = name;
    }

    public TeacherDto(Teacher teacher){
        id = teacher.getId();
        pw = teacher.getPw();
        name = teacher.getName();
    }

    public Teacher toEntity(){
        return Teacher.builder()
                .id(id)
                .pw(pw)
                .name(name)
                .build();
    }

}
