package com.kona.soogang.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Register {

    @Column(name="cancel_status")
    private String cancelStatus;

    @Id
    @ManyToOne
    @JoinColumn(name="lecture_name")
    private Lecture lecture;

    @Id
    @ManyToOne
    @JoinColumn(name="student_name")
    private Student student;

}
