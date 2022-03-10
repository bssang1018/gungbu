package com.kona.soogang.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, String> {

    List<Teacher> findByIdAndPw(String id, String pw);

}
