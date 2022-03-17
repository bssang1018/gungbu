package com.kona.soogang.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Student {

    @Id
    @Column(name="student_email")
    private String email;

    @Column(name="student_name")
    private String name;

    @Column(name="student_pw")
    private String pw;

    @Column(name="join_status")
    private String joinStatus;
    //BY: 회원가입전에 추천받음, 현재 미가입상태
    //BN: 회원가입전에 추천받음, 현재 가입상태
    //AY: 회원가입후에 추천받음
    //NO: 추천받지 못함

    //연관관계 매핑
    @ManyToOne
    @JoinColumn(name="teacher_id")
    private Teacher teacher;

    @Builder
    public Student(Teacher teacher, String email, String name, String pw, String joinStatus){
        this.teacher = teacher;
        this.email = email;
        this.pw = pw;
        this.name = name;
        this.joinStatus = joinStatus;
    }
}
