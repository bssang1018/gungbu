package com.kona.soogang.service;

import com.kona.soogang.domain.Lecture;
import com.kona.soogang.domain.LectureRepository;
import com.kona.soogang.dto.LectureDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class CommonService {

    private final LectureRepository lectureRepository;

    public CommonService(LectureRepository lectureRepository) {
        this.lectureRepository = lectureRepository;
    }

    //강의리스트 조회
    public ResponseEntity<List<LectureDto>> lectureList(Pageable pageable) throws IllegalStateException {
        Page<Lecture> lectureList = lectureRepository.findAll(pageable);

        if (lectureList.isEmpty()){
            throw new IllegalStateException();
        }

        List<LectureDto> lectureDtos = lectureList.getContent().stream()
                .map(dto -> LectureDto.builder()
                        .lectureCode(dto.getLectureCode())
                        .lectureName(dto.getLectureName())
                        .closeStatus(dto.getCloseStatus())
                        .maxPerson(dto.getMaxPerson())
                        .teacherNum(dto.getTeacher().getTeacherNum())
                        .build()
                ).collect(Collectors.toList());
        return new ResponseEntity<>(lectureDtos, HttpStatus.OK);

    }
}
