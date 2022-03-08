package com.kona.soogang.service;

import com.kona.soogang.domain.Student;
import com.kona.soogang.domain.Teacher;
import com.kona.soogang.domain.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;

    public String serviceHello() {
        return "hello";
    }

    public void teacherJoin(String teacher_id, String teacher_pw, String teacher_name) {
        Teacher teacher = new Teacher();
        teacher.setTeacher_id(teacher_id);
        teacher.setTeacher_pw(teacher_pw);
        teacher.setTeacher_name(teacher_name);

        teacherRepository.save(teacher);
    }

}
