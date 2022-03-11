package com.kona.soogang.service;

import com.kona.soogang.domain.Lecture;
import com.kona.soogang.domain.LectureRepository;
import com.kona.soogang.dto.LectureDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CommonService {

    private final LectureRepository lectureRepository;

    public List<LectureDto> lectureList() {
        List<Lecture> lectureList = lectureRepository.findAll();
        // 스트림 사용해 보기
        return lectureList.stream().map(LectureDto::new).collect(Collectors.toList());
    }
}
