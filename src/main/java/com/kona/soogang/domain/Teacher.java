package com.kona.soogang.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Teacher {

    @Id
    @Column(name="teacher_num")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teacherNum;

    @Column(name="teacher_id")
    private String teacherId;

    @Column(name="teacher_name")
    private String teacherName;

    @Builder
    public Teacher(Long teacherNum, String teacherId, String teacherName){
        this.teacherNum = teacherNum;
        this.teacherId = teacherId;
        this.teacherName = teacherName;
    }

}
