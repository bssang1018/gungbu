package com.kona.soogang.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, String> {

    List<Student> findByEmailAndPw(String email, String pw);

    List<Student> findByEmail(String email);
}
