package com.kona.soogang.service;

import com.kona.soogang.domain.Student;
import com.kona.soogang.domain.StudentRepository;
import com.kona.soogang.dto.StudentReq;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class StudentServiceTest extends TestCase {

    @Mock
    StudentRepository studentRepository;

    @Autowired
    StudentService studentService;

    @Test
    public void testStudentJoinService() throws Exception{
        //given
        StudentReq studentReq = StudentReq.builder().email("tttt@test.com").name("tester").build();
        Student student = Student.builder().email(studentReq.getEmail()).name(studentReq.getName()).build();

        Long fakeId = 1L;
        ReflectionTestUtils.setField(student, "studentNum", fakeId);

        //mocking
        given(studentRepository.save(any())).willReturn(student);
        given(studentRepository.findById(fakeId)).willReturn(Optional.ofNullable(student));

        //when
        Long newStudentId = studentService.studentJoin(studentReq).getBody().getStudentNum();

        //then
        Optional<Student> findStudent = studentRepository.findById(newStudentId);

        assertEquals(student.getStudentNum(), findStudent.get().getStudentNum());
    }

    @Test
    public void studentJoinDulplTest() throws Exception{
        //given
        StudentReq studentReq = StudentReq.builder().email("tttt@test.com").name("tester").build();
        Student student = Student.builder().email(studentReq.getEmail()).name(studentReq.getName()).build();

        StudentReq studentReq2 = StudentReq.builder().email("tttt@test.com").name("tester").build();
        Student student2 = Student.builder().email(studentReq.getEmail()).name(studentReq.getName()).build();

        Long fakeId = 1L;
        Long fakeId2 = 2L;
        ReflectionTestUtils.setField(student, "studentNum", fakeId);
        ReflectionTestUtils.setField(student2, "studentNum", fakeId2);

        //mocking
        given(studentRepository.save(any())).willReturn(student);
        given(studentRepository.findById(fakeId)).willReturn(Optional.ofNullable(student));
        given(studentRepository.save(any())).willThrow();

        //when
        studentService.studentJoin(studentReq);

        //then
        studentService.studentJoin(studentReq2).getStatusCode();
    }
}