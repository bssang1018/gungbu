package com.kona.soogang.controller;

import com.kona.soogang.domain.Lecture;
import com.kona.soogang.domain.LectureRepository;
import com.kona.soogang.dto.LectureDto;
import com.kona.soogang.exception.TestException;
import com.kona.soogang.exception.TestHttpResponseCode;
import com.kona.soogang.service.CommonService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api(tags = {"Common"}, hidden = true)
public class CommonController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //@RequiredArgsConstructor 사용하지 않고, 직접 생성자 명시
    private final CommonService commonService;
    private final LectureRepository lectureRepository;

    public CommonController(CommonService commonService, LectureRepository lectureRepository) {
        this.commonService = commonService;
        this.lectureRepository = lectureRepository;
    }

    //첫페이지
    @GetMapping(value = "/")
    public String firstPage() {
        logger.info("메인 페이지 요청");
        //throw new TestException(TestHttpResponseCode.RESULT_NOT_FOUND);
        return "안녕하세요! 만나서 반갑습니다.";
    }

    //강의 조회는
    //기본적으로 가나다 순
    //폐강 아닌 강의만 조회, 또는 폐강 강의 포함 조회
    @GetMapping(value = "/lectureList")
    public ResponseEntity<Page<LectureDto>> lectureList(int page, int size, String sort) {
        logger.info("강의 리스트 조회:: "+page+"/"+size+"/"+sort);
        return commonService.lectureList(page, size, sort);
    }
}