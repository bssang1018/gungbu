package com.kona.soogang.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kona.soogang.domain.Student;
import com.kona.soogang.domain.StudentRepository;
import com.kona.soogang.dto.StudentReq;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class StudentControllerTest extends TestCase {

//    @LocalServerPort
//    private int port;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

//    @Autowired
//    private TestRestTemplate testRestTemplate;

//    @Autowired
//    private StudentRepository studentRepository;

//    @After
//    public void tearDown() throws Exception {
//        studentRepository.deleteAll();
//    }

    @Test
    public void joinDataTest() throws Exception {
        //회원가입 파라미터 테스트
        //이메일 형식이 아니거나, 공백, null => 예외
        StudentReq studentReq = new StudentReq().builder()
                .email("123@naver.com")
                .build();

        String str = objectMapper.writeValueAsString(studentReq);

        mvc.perform(post("/student/join").contentType(MediaType.APPLICATION_JSON)
                .content(str))
                .andExpect(status().isOk());

    }


}