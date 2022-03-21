package com.kona.soogang.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RegisterRepository extends JpaRepository<Register, Long> {

    @Query(value = "SELECT COUNT(*) FROM register WHERE lecture_code=?1"
            , nativeQuery = true)
    int findAllByLectureCode(Long lectureCode);

    Optional<Register> findByLecture_LectureCodeAndStudent_StudentNum(Long lectureCode, Long studentNum);

    Page<Register> findAllByStudent_StudentNum(Long studentNum, Pageable pageable);

}
