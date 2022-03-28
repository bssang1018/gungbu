package com.kona.soogang.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class StudentReq {

    @NotNull
    @Email
    @NotEmpty
    private String email;

    @NotNull
    @NotEmpty
    private String name;

    @Builder
    public StudentReq(String email, String name){
        this.email = email;
        this.name = name;
    }

}
