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

    private Long teacherNum;
    private String teacherId;
    private String teacherName;

    @Builder
    public TeacherDto(Long teacherNum, String teacherId, String teacherName){
        this.teacherNum = teacherNum;
        this.teacherId = teacherId;
        this.teacherName = teacherName;
    }

    public TeacherDto(Teacher teacher){
        this.teacherNum = teacher.getTeacherNum();
        this.teacherId = teacher.getTeacherId();
        this.teacherName = teacher.getTeacherName();
    }

    public Teacher toEntity(){
        return Teacher.builder()
                .teacherNum(teacherNum)
                .teacherId(teacherId)
                .teacherName(teacherName)
                .build();
    }

}
