package com.kona.soogang.dto;

import com.kona.soogang.domain.Register;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RegisterResponse {

    private Long registerId;
    private String cancelStatus;
    private Long lectureCode;
    private Long studentNum;

    @Builder
    public RegisterResponse(Long registerId, String cancelStatus, Long lectureCode, Long studentNum){
        this.registerId = registerId;
        this.cancelStatus = cancelStatus;
        this.lectureCode = lectureCode;
        this.studentNum = studentNum;
    }

    public RegisterResponse(Register register){
        this.registerId = register.getRegisterId();
        this.cancelStatus = register.getCancelStatus();
        this.lectureCode = register.getLecture().getLectureCode();
        this.studentNum = register.getStudent().getStudentNum();
    }

}
