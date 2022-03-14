package com.kona.soogang.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
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

//    @Builder
//    public Student(Teacher teacher_id, String student_email, String student_name, String student_pw, String join_status){
//        this.teacher = teacher_id;
//        this.student_email = student_email;
//        this.student_name = student_name;
//        this.student_pw = student_pw;
//        this.join_status = join_status;
//    }
}
