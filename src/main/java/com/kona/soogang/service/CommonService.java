package com.kona.soogang.service;

import com.kona.soogang.domain.Lecture;
import com.kona.soogang.domain.LectureRepository;
import com.kona.soogang.dto.LectureDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public List<LectureDto> lectureList(Pageable pageable) {
        Page<Lecture> lectureList = lectureRepository.findAll(pageable);

        if (lectureList.isEmpty()){
            //조회결과가 존재하지 않는다면
            throw new IllegalStateException();
        }

        // 스트림 사용해 보기
        return lectureList.stream().map(LectureDto::new).collect(Collectors.toList());
    }
}
