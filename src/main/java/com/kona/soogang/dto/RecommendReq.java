package com.kona.soogang.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class RecommendReq {

    @NotBlank
    private String teacherId;

    @NotBlank
    @Email
    private String email;

    // @Email 어노테이션은 null을 허용하기 때문에 주의해야 한다!
}
