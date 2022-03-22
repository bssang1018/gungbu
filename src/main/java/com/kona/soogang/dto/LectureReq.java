package com.kona.soogang.dto;

import com.kona.soogang.domain.Lecture;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LectureReq {

    private String teacherId;
    private String lectureName;
    private int maxPerson;

}
