package com.kona.soogang.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class LectureReq {

    @NotBlank
    private String teacherId;

    @NotBlank
    private String lectureName;

    private int maxPerson;

}
