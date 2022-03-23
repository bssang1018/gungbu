package com.kona.soogang.service;

import com.kona.soogang.domain.Lecture;
import com.kona.soogang.domain.LectureRepository;
import com.kona.soogang.dto.LectureDto;
import com.kona.soogang.exception.TestException;
import com.kona.soogang.exception.TestHttpResponseCode;
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

    public ResponseEntity<Page<LectureDto>> lectureList(int page, int size, String sort) {
        if(sort==null || sort.trim().isEmpty() || Integer.valueOf(page)==null || Integer.valueOf(size)==null){
            throw new IllegalStateException();
        }
        Page<Lecture> lecturePage = lectureRepository.findAll(PageRequest.of(page-1, size, Sort.Direction.ASC, sort));
        Page<LectureDto> lectureDtos = lecturePage.map(LectureDto::new);
        return new ResponseEntity<>(lectureDtos, HttpStatus.OK);
    }
}
