package com.kona.soogang.dto;

import com.kona.soogang.domain.Lecture;
import com.kona.soogang.domain.Teacher;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class LectureDto {

    private Long lectureCode;
    private String lectureName;
    private String closeStatus;
    private int maxPerson;

    private String id;


    @Builder
    public LectureDto(Long lectureCode, String lectureName, int maxPerson, String closeStatus, String id){
        this.lectureCode = lectureCode;
        this.lectureName = lectureName;
        this.maxPerson = maxPerson;
        this.closeStatus = closeStatus;

        this.id = id;
    }

    public LectureDto(Lecture lecture){
        this.lectureCode = lecture.getLectureCode();
        this.lectureName = lecture.getLectureName();
        this.maxPerson = lecture.getMaxPerson();
        this.closeStatus = lecture.getCloseStatus();

        this.id = lecture.getTeacher().getId();
    }

    public Lecture toEntity(){
        return Lecture.builder()
                    .lectureCode(lectureCode)
                    .lectureName(lectureName)
                    .maxPerson(maxPerson)
                    .closeStatus(closeStatus)
                    .teacher(Teacher.builder().id(id).build())
                    .build();
    }
}
