package com.kona.soogang.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Teacher {

    @Id
    @Column(name="teacher_id")
    private String id;

    @Column(name="teacher_pw")
    private String pw;

    @Column(name="teacher_name")
    private String name;

    @Builder
    public Teacher(String id, String pw, String name){
        this.id = id;
        this.pw = pw;
        this.name = name;
    }

}
