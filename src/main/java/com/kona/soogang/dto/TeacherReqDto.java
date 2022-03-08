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

    private String teacher_id;
    private String teacher_name;
    private String teacher_pw;

    @Builder
    public TeacherReqDto(String teacher_id, String teacher_name, String teacher_pw){
        this.teacher_id = teacher_id;
        this.teacher_name = teacher_name;
        this.teacher_pw = teacher_pw;
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
