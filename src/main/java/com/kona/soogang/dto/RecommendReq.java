package com.kona.soogang.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class RecommendReq {

    @NotNull
    @NotEmpty
    private String teacherId;

    @NotNull
    @Email
    @NotEmpty
    private String email;

}
