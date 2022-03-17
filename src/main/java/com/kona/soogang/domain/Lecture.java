package com.kona.soogang.domain;

import lombok.*;

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
    @ManyToOne
    @JoinColumn(name="teacher_id")
    public Teacher teacher;

    @Builder
    public Lecture(Teacher teacher, Long lectureCode, String lectureName, String closeStatus, int maxPerson){
        this.teacher = teacher;
        this.lectureCode = lectureCode;
        this.lectureName = lectureName;
        this.closeStatus = closeStatus;
        this.maxPerson = maxPerson;
    }
}
