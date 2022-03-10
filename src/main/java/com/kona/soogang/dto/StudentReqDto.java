package com.kona.soogang.dto;


import com.kona.soogang.domain.Student;
import com.kona.soogang.domain.Teacher;
import com.kona.soogang.domain.TeacherRepository;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StudentReqDto {

    private String email;
    private String name;
    private String pw;
    private String joinStatus;

    private String id;

    @Builder
    public StudentReqDto(String id, String email, String name, String pw, String status){
        this.id = id;

        this.email = email;
        this.name = name;
        this.pw = pw;
        this.joinStatus = joinStatus;
    }

}
