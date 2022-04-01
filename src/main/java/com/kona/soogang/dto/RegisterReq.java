package com.kona.soogang.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Setter
@Getter
@NoArgsConstructor
public class RegisterReq {

    @NotBlank
    private String lectureName;

    @NotBlank
    @Email
    private String email;

}
