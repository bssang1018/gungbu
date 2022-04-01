package com.kona.soogang.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class StudentReq {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String name;

    @Builder
    public StudentReq(String email, String name){
        this.email = email;
        this.name = name;
    }

}
