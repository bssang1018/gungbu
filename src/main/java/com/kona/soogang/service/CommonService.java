package com.kona.soogang.service;

import com.kona.soogang.domain.Lecture;
import com.kona.soogang.domain.LectureRepository;
import com.kona.soogang.dto.LectureDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class CommonService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final LectureRepository lectureRepository;

    public CommonService(LectureRepository lectureRepository) {
        this.lectureRepository = lectureRepository;
    }

//    //강의리스트 조회
//    public ResponseEntity<List<Lecture>> lectureList(String status,Pageable pageable){
//        Page<Lecture> lecturePage = lectureRepository.findAllByCloseStatus(status, pageable);
//        lecturePage.map()
//        return new ResponseEntity<>(lecturePage,HttpStatus.OK);
//    }
}
