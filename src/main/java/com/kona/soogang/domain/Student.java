package com.kona.soogang.domain;

import lombok.*;

import javax.persistence.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Student {

    @Id
    private String student_email;

    private String student_name;

    private String student_pw;

    private String join_status;

    private String recomended_teacher_id;


//    @Builder
//    public Student(Teacher teacher_id, String student_email, String student_name, String student_pw, String join_status){
//        this.teacher = teacher_id;
//        this.student_email = student_email;
//        this.student_name = student_name;
//        this.student_pw = student_pw;
//        this.join_status = join_status;
//    }
}
