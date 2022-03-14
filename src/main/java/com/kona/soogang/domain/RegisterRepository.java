package com.kona.soogang.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RegisterRepository extends JpaRepository<Register, RegisterId> {

    List<Register> findByRegisterId(RegisterId registerId);

    @Query(value = "SELECT COUNT(*) FROM register WHERE lecture_code=?1"
            , nativeQuery = true)
    int countRegistered(long lectureCode);

    @Modifying
    @Query(value = "UPDATE register SET cancel_status='YES' WHERE lecture_code=?1 AND student_email=?2"
            , nativeQuery = true)
    void registerCancel(Long lectureCode, String email);

}
