package com.kona.soogang.controller;

import com.kona.soogang.dto.LectureDto;
import com.kona.soogang.service.CommonService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CommonController {

    //@RequiredArgsConstructor 사용하지 않고, 직접 생성자 명시
    private final CommonService commonService;

    public CommonController(CommonService commonService){
        this.commonService = commonService;
    }

    //첫페이지
    @GetMapping(value = "/")
    public String firstPage(){
        System.out.println("메인 페이지 요청");
        return "안녕하세요! 만나서 반갑습니다.";
    }

    //강의 조회
    //Pageable 사용해보기
    @GetMapping(value = "/lectureList")
    public List<LectureDto> lectureList(Pageable pageable){
        System.out.println("강의 리스트 조회");
        return commonService.lectureList(pageable);
    }


}
