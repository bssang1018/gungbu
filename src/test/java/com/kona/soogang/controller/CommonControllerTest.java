package com.kona.soogang.controller;

import com.kona.soogang.controller.CommonController;
import com.kona.soogang.domain.LectureRepository;
import com.kona.soogang.service.CommonService;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.GsonTester;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class CommonControllerTest extends TestCase {

    @Autowired
    private MockMvc mvc;

    @Test
    public void 메인페이지테스트() throws Exception{
        mvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    public void 강의리트스테스트() throws Exception{
        HashMap<String, Object> map = new HashMap<>();

        mvc.perform(get("/lectureList")
        .param("page",String.valueOf(1))
        .param("size", String.valueOf(1))
        .param("sort",String.valueOf("closeStatus")))
                .andExpect(status().isOk())
                .andDo(print());
    }

}