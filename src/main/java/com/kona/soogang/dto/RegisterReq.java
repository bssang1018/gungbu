package com.kona.soogang.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
public class RegisterReq {

    @NotNull
    @NotEmpty
    private String lectureName;

    @NotNull
    @NotEmpty
    @Email
    private String email;

}
