package com.kona.soogang.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lecture {

    @Id
    @Column(name="lecture_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lectureCode;

    @Column(name="lecture_name")
    private String lectureName;

    @Column(name="close_status")
    private String closeStatus;

    //연관관계 매핑
    @ManyToOne
    @JoinColumn(name="teacher_id")
    public Teacher teacher;

}
