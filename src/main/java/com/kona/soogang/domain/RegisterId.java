package com.kona.soogang.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
public class RegisterId implements Serializable {
    //복합키 관리 클래스

    @Column(name="lecture_code")
    private Long lectureCode;

    @Column(name="student_email")
    private String studentEmail;

    @Builder
    public RegisterId(Long lectureCode, String studentEmail){
        this.lectureCode = lectureCode;
        this.studentEmail = studentEmail;
    }

}


