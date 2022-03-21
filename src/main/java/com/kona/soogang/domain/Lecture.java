package com.kona.soogang.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Lecture {

    @Id
    @Column(name="lecture_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lectureCode;

    @Column(name="lecture_name")
    private String lectureName;

    @Column(name="close_status")
    private String closeStatus;

    @Column(name="max_Person")
    private int maxPerson;

    //연관관계 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="teacher_num")
    public Teacher teacher;

    @Builder
    public Lecture(Long lectureCode, String lectureName, String closeStatus, int maxPerson, Teacher teacher){
        this.lectureCode = lectureCode;
        this.lectureName = lectureName;
        this.closeStatus = closeStatus;
        this.maxPerson = maxPerson;
        this.teacher = teacher;
    }

    public void setCloseStatus(String closeStatus){
        this.closeStatus = closeStatus;
    }
}
