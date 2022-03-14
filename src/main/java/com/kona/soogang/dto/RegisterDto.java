package com.kona.soogang.dto;

import com.kona.soogang.domain.Register;
import com.kona.soogang.domain.RegisterId;
import lombok.Data;

import java.util.Date;

@Data
public class RegisterDto {

    private Long lectureCode;
    private String studentEmail;
    private String cancelStatus;
    private Date timestamp;

    public RegisterDto(Register register){
        this.lectureCode = register.getRegisterId().getLectureCode();
        this.studentEmail = register.getRegisterId().getStudentEmail();
        this.cancelStatus = register.getCancelStatus();
        this.timestamp = register.getTimestamp();
    }


}
