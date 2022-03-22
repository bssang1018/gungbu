package com.kona.soogang.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Student {

    @Id
    @Column(name="student_num")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentNum;

    @Column(name="student_email")
    private String email;

    @Column(name="student_name")
    private String name;

    @Column(name="join_status")
    private String joinStatus;
    //BY: 회원가입전에 추천받음, 현재 미가입상태
    //BN: 회원가입전에 추천받음, 현재 가입상태
    //AY: 회원가입후에 추천받음
    //NO: 추천받지 못함

    //연관관계 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="teacher_num", foreignKey = @ForeignKey, nullable = true)
    private Teacher teacher;

    @Builder
    public Student(Long studentNum, String email, String name, String joinStatus, Teacher teacher){
        this.studentNum = studentNum;
        this.email = email;
        this.name = name;
        this.joinStatus = joinStatus;
        this.teacher = teacher;
    }

    public void studentUpdate(String name,String joinStatus){
        this.name = name;
        this.joinStatus = joinStatus;
    }

    public void setJoinStatus(String joinStatus){
        this.joinStatus = joinStatus;
    }

    public void setTeacher(Teacher teacher){
        this.teacher = teacher;
    }

}
