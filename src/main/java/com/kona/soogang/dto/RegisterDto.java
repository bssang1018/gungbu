package com.kona.soogang.dto;

import com.kona.soogang.domain.Lecture;
import com.kona.soogang.domain.Register;
import com.kona.soogang.domain.Student;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterDto {

    private Long registerId;
    private String cancelStatus;
    private Long lectureCode;
    private Long studentNum;


    @Builder
    public RegisterDto(Long registerId, String cancelStatus, Long lectureCode, Long studentNum){
        this.registerId = registerId;
        this.cancelStatus = cancelStatus;
        this.lectureCode = lectureCode;
        this.studentNum = studentNum;
    }

    public RegisterDto(Register register){
        this.registerId = register.getRegisterId();
        this.cancelStatus = register.getCancelStatus();
        this.lectureCode = register.getLecture().getLectureCode();
        this.studentNum = register.getStudent().getStudentNum();
    }

    public Register toEntity(){
        return Register.builder()
                .registerId(registerId)
                .cancelStatus(cancelStatus)
                .lecture(Lecture.builder().lectureCode(lectureCode).build())
                .student(Student.builder().studentNum(studentNum).build())
                .build();
    }
}
