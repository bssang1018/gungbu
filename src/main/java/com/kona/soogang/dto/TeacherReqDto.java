package com.kona.soogang.dto;

import com.kona.soogang.domain.Teacher;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TeacherReqDto {

    private String id;
    private String name;
    private String pw;

    @Builder
    public TeacherReqDto(String id, String name, String pw){
        this.id = id;
        this.name = name;
        this.pw = pw;
    }

    //필요없을 듯...
   /* public Teacher toEntity(){
        return Teacher.builder()
                .teacher_id(teacher_id)
                .teacher_name(teacher_name)
                .teacher_pw(teacher_pw)
                .build();
    }*/
}
