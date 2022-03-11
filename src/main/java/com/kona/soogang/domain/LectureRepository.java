package com.kona.soogang.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LectureRepository extends JpaRepository<Lecture, Long> {

    Optional<Lecture> findByLectureName(String lectureName);

}
