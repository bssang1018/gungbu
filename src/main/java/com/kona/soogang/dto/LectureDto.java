package com.kona.soogang.dto;

import com.kona.soogang.domain.Lecture;
import com.kona.soogang.domain.Teacher;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class LectureDto {

    private Long lectureCode;
    private String lectureName;
    private String closeStatus;
    private int maxPerson;
    private Long teacherNum;


    @Builder
    public LectureDto(Long lectureCode, String lectureName, int maxPerson, String closeStatus, Long teacherNum){
        this.lectureCode = lectureCode;
        this.lectureName = lectureName;
        this.maxPerson = maxPerson;
        this.closeStatus = closeStatus;
        this.teacherNum = teacherNum;
    }

    public LectureDto(Lecture lecture){
        this.lectureCode = lecture.getLectureCode();
        this.lectureName = lecture.getLectureName();
        this.maxPerson = lecture.getMaxPerson();
        this.closeStatus = lecture.getCloseStatus();
        this.teacherNum = lecture.getTeacher().getTeacherNum();
    }

    public Lecture toEntity(){
        return Lecture.builder()
                    .lectureCode(lectureCode)
                    .lectureName(lectureName)
                    .maxPerson(maxPerson)
                    .closeStatus(closeStatus)
                    .teacher(Teacher.builder().teacherNum(teacherNum).build())
                    .build();
    }

}
