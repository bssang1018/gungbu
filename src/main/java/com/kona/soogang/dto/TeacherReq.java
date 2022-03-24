package com.kona.soogang.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class TeacherReq {

    @NotNull
    @NotEmpty
    private String teacherId;

    @NotNull
    @NotEmpty
    private String teacherName;
}
