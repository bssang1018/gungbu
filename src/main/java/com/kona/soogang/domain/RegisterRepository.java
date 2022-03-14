package com.kona.soogang.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RegisterRepository extends JpaRepository<Register, RegisterId> {

    List<Register> findByRegisterId(RegisterId registerId);

    @Query(value = "SELECT COUNT(*) FROM register WHERE lecture_name=?1"
            , nativeQuery = true)
    int countRegistered(String lectureName);
}
