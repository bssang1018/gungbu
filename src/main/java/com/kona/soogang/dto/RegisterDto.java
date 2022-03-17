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
    private String studentEmail;

    @Builder
    public RegisterDto(String cancelStatus, Date timestamp, Long lectureCode, String studentEmail){
        this.cancelStatus = cancelStatus;
        this.timestamp = timestamp;

        this.lectureCode = lectureCode;
        this.studentEmail = studentEmail;
    }

    //강의 등록 dto 수정하기
    public RegisterDto(Register register){
        this.cancelStatus = register.getCancelStatus();
        this.timestamp = register.getTimestamp();

        this.lectureCode = register.getRegisterId().getLectureCode();
        this.studentEmail = register.getRegisterId().getStudentEmail();
    }

    public Register toEntity(){
        return Register.builder()
                .cancelStatus(cancelStatus)
                .timestamp(timestamp)
                .registerId(RegisterId.builder().lectureCode(lectureCode).studentEmail(studentEmail).build())
                .build();
    }

}
