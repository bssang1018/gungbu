package com.kona.soogang.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LectureRepository extends JpaRepository<Lecture, Long> {

    List<Lecture> findByLectureName(long lectureCode);

    @Modifying
    @Query(value = "UPDATE lecture SET close_status='YES' WHERE lecture_code=?1"
            , nativeQuery = true)
    void lectureClose(Long lectureCode);
}
