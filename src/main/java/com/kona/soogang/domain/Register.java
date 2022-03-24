package com.kona.soogang.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Register {

    @Id
    @Column(name = "register_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long registerId;

    @Column(name="cancel_status")
    private String cancelStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_code")
    private Lecture lecture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_num")
    private Student student;

    @Builder
    public Register(Long registerId, String cancelStatus, Lecture lecture, Student student){
        this.registerId = registerId;
        this.cancelStatus = cancelStatus;
        this.lecture = lecture;
        this.student = student;
    }

    public void cancelUpdate(String status){
        this.cancelStatus = status;
    }
}
