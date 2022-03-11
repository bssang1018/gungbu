package com.kona.soogang.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, String> {

    List<Student> findByEmailAndPw(String email, String pw);

    List<Student> findByEmail(String email);

    @Modifying
    @Query(value="UPDATE student SET join_status=?1, teacher_id=?2 WHERE student_email=?3"
            , nativeQuery = true)
    void recommendUpdate(String joinStatus, String id, String email);
}
