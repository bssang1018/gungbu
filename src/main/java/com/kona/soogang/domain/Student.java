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
    @Column(name="student_email")
    private String email;

    @Column(name="student_name")
    private String name;

    @Column(name="student_pw")
    private String pw;

    @Column(name="join_status")
    private String joinStatus;

    @Column(name="recommended_teacher_id")
    private String recommendedTeacherId;


//    @Builder
//    public Student(Teacher teacher_id, String student_email, String student_name, String student_pw, String join_status){
//        this.teacher = teacher_id;
//        this.student_email = student_email;
//        this.student_name = student_name;
//        this.student_pw = student_pw;
//        this.join_status = join_status;
//    }
}
