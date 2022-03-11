package com.kona.soogang.dto;

import com.kona.soogang.domain.Lecture;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class LectureDto {

    private Long lectureCode;
    private String lectureName;
    private String closeStatus;
    private String teacherId;

    public LectureDto(Lecture lecture){
        this.lectureCode = lecture.getLectureCode();
        this.lectureName = lecture.getLectureName();
        this.closeStatus = lecture.getCloseStatus();
        this.teacherId = lecture.getTeacher().getId();
    }
}
