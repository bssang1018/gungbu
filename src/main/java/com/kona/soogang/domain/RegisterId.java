package com.kona.soogang.domain;

import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
public class RegisterId implements Serializable {

    //복합키 관리 클래스
    
    @Column(name="lecture_name")
    private String lectureName;

    @Column(name="student_name")
    private String studentName;

    public RegisterId(String lectureName, String studentName){
        this.lectureName = lectureName;
        this.studentName = studentName;
    }

}


