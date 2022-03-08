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
public class Teacher {

    @Id
    private String teacher_id;

    private String teacher_pw;

    private String teacher_name;

//    @Builder
//    public Teacher(String teacher_id, String teacher_name, String teacher_pw){
//        this.teacher_id = teacher_id;
//        this.teacher_name = teacher_name;
//        this.teacher_pw = teacher_pw;
//    }

    // @Column
    // 문자열의 경우 VARCHAR(255)가 기본. 사이즈를 변경하고 싶거나 타입을 변경하고 싶을 때 @Column을 사용한다.

}
