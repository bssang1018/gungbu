package com.kona.soogang.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kona.soogang.domain.Student;
import com.kona.soogang.domain.StudentRepository;
import com.kona.soogang.dto.StudentReq;
import com.kona.soogang.service.StudentService;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(StudentController.class)
public class StudentControllerTest extends TestCase {

    @Autowired
    private MockMvc mvc;

    @MockBean
    StudentService studentService;

    @Autowired
    StudentController studentController;

    @Test
    public void recommendTest() throws Exception{
        String email = "student1@test.com";
        given(studentService.recommendedMe(email)).willReturn(
                new ResponseEntity<>("test1", HttpStatus.OK));

        mvc.perform(
                get("/student/recommendedMe/"+email))
                .andExpect(status().isNotFound())
                .andDo(print());

        verify(studentService).recommendedMe(email);

    }


}