package com.kona.soogang.dto;

import com.kona.soogang.domain.Lecture;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class LectureReq {

    @NotNull
    @NotEmpty
    private String teacherId;

    @NotNull
    @NotEmpty
    private String lectureName;

    @NotNull
    @NotEmpty
    private int maxPerson;

}
