package com.kona.soogang.dto;

import com.kona.soogang.domain.Register;
import com.kona.soogang.domain.RegisterId;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class RegisterDto {

    private String cancelStatus;
    private Date timestamp;

    private Long lectureCode;
    private String email;

    @Builder
    public RegisterDto(String cancelStatus, Date timestamp, Long lectureCode, String email){
        this.cancelStatus = cancelStatus;
        this.timestamp = timestamp;

        this.lectureCode = lectureCode;
        this.email = email;
    }

    public RegisterDto(Register register){
        this.cancelStatus = register.getCancelStatus();
        this.timestamp = register.getTimestamp();

        this.lectureCode = register.getRegisterId().getLectureCode();
        this.email = register.getRegisterId().getEmail();
    }

    public Register toEntity(){
        return Register.builder()
                .cancelStatus(cancelStatus)
                .timestamp(timestamp)
                .registerId(RegisterId.builder().lectureCode(lectureCode).email(email).build())
                .build();
    }

}
