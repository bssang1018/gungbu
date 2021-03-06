package com.kona.soogang.service;

import com.kona.soogang.domain.*;
import com.kona.soogang.dto.*;
import com.kona.soogang.exception.TestException;
import com.kona.soogang.exception.TestHttpResponseCode;
import org.aspectj.weaver.ast.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class StudentService {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final LectureRepository lectureRepository;
    private final RegisterRepository registerRepository;

    public StudentService(StudentRepository studentRepository, LectureRepository lectureRepository, RegisterRepository registerRepository, TeacherRepository teacherRepository) {
        this.studentRepository = studentRepository;
        this.lectureRepository = lectureRepository;
        this.registerRepository = registerRepository;
        this.teacherRepository = teacherRepository;
    }

    /**
    * 신규 학생가입
    * */
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<StudentDto> newJoin(StudentReq studentReq){
        Student studentForSave = new Student()
                .builder()
                .email(studentReq.getEmail())
                .name(studentReq.getName())
                .joinStatus("NO")
                .build();
        studentRepository.save(studentForSave);

        StudentDto dto = new StudentDto().builder()
                .studentNum(studentForSave.getStudentNum())
                .email(studentForSave.getEmail())
                .name(studentForSave.getName())
                .joinStatus(studentForSave.getJoinStatus())
                .build();
        return new ResponseEntity<StudentDto>(dto, HttpStatus.CREATED);
    }

    /**
     * 선생의 추천을 통해 가입된 학생의 상태를 업데이트
     * */
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<StudentDto> joinStatusUp(Student student){
        StudentDto dto = StudentDto.builder()
                .studentNum(student.getStudentNum())
                .email(student.getEmail())
                .joinStatus(student.getJoinStatus())
                .teacherNum(student.getTeacher().getTeacherNum())
                .build();
        return new ResponseEntity<StudentDto>(dto, HttpStatus.CREATED);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<StudentDto> studentJoin(StudentReq studentReq) {
        Optional<Student> student = studentRepository.findByEmail(studentReq.getEmail());
        if (student.isPresent() && student.get().getJoinStatus().equals("BN")) {
            //중복결과가 존재하면서, 선생의 추천으로 이메일만 이미 디비에 등록된 경우
            //해당 레코드의 상태를 업데이트
            student.get().studentUpdate(studentReq.getName(), "BY");
            joinStatusUp(student.get());
        }else {
            //중복결과가 존재면면서, 상태가 NO, AY, BY면
            //단순 이메일 중복으로 인한 가입 거절
            throw new TestException(TestHttpResponseCode.IDOREMAIL_DUPLICATE);
        }
        //조회결과 없다면 신규가입 진행
        return newJoin(studentReq);
    }

    //수강신청
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<RegisterDto> lectureRegister(RegisterReq registerReq) {

        //강의명과 email로 해당 키값 가져오기
        Optional<Lecture> lecture = lectureRepository.findByLectureName(registerReq.getLectureName());
        if (!lecture.isPresent()) {
            throw new TestException(TestHttpResponseCode.RESULT_NOT_FOUND);
        }
        Long lectureCode = lecture.get().getLectureCode();

        Optional<Student> student = studentRepository.findByEmail(registerReq.getEmail());
        if (!student.isPresent()) {
            throw new TestException(TestHttpResponseCode.RESULT_NOT_FOUND);
        }
        Long studentNum = student.get().getStudentNum();
        int maxPerson = lecture.get().getMaxPerson();

        Pageable pageable = PageRequest.of(20,2000);
        long presentPerson= registerRepository.findAllByLecture_LectureCodeAndCancelStatus(lectureCode,"NO",pageable).getTotalElements();

        //신청인원이 초과한 경우, 수강신청 불가능
        //lectureCode로 카운트
        if (presentPerson >= maxPerson) {

            if (registerRepository.findByLecture_LectureCodeAndStudent_StudentNum(lectureCode, studentNum).isPresent()) {
                throw new TestException(TestHttpResponseCode.REGISTER_DUPLICATE);
            }

            //수용인원을 초과하고, 학생의 추천상태가 AY또는 BY인 경우에는 수강신청 프리패스
            if (!studentRepository.findById(studentNum).get().getJoinStatus().equals("NO")) {
                RegisterDto registerDto = new RegisterDto();
                registerDto.setLectureCode(lectureCode);
                registerDto.setStudentNum(studentNum);
                registerDto.setCancelStatus("NO");
                Register registerResult = registerRepository.save(registerDto.toEntity());
                //삽입한 레코드 조회해서 반환하기
                return new ResponseEntity<>(new RegisterDto()
                        .builder()
                        .registerId(registerResult.getRegisterId())
                        .lectureCode(registerResult.getLecture().getLectureCode())
                        .studentNum(registerResult.getStudent().getStudentNum())
                        .cancelStatus(registerResult.getCancelStatus())
                        .build()
                        , HttpStatus.OK);
            }
            //수강 신청 인원 초과
            throw new TestException(TestHttpResponseCode.MAX_ISSUE);
        }

        //이미 수강신청을 한 강의의 경우, 중복신청 불가능
        //lectureCode와 studentNum 이 둘다 겹치면 중복으로 판단
        if (registerRepository.findByLecture_LectureCodeAndStudent_StudentNum(lectureCode, studentNum).isPresent()) {
            throw new TestException(TestHttpResponseCode.REGISTER_DUPLICATE);
        }
        //수강 신청
        RegisterDto registerDto = new RegisterDto();
        registerDto.setLectureCode(lectureCode);
        registerDto.setStudentNum(studentNum);
        registerDto.setCancelStatus("NO");
        Register registerResult = registerRepository.save(registerDto.toEntity());
        return new ResponseEntity<>(RegisterDto.builder()
                .registerId(registerResult.getRegisterId())
                .lectureCode(registerResult.getLecture().getLectureCode())
                .studentNum(registerResult.getStudent().getStudentNum())
                .cancelStatus(registerResult.getCancelStatus())
                .build()
                , HttpStatus.CREATED);
    }

    //수강신청 취소
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<RegisterDto> registerCancel(RegisterReq registerReq) {

        Optional<Lecture> lecture = lectureRepository.findByLectureName(registerReq.getLectureName());
        if (!lecture.isPresent()) {
            throw new TestException(TestHttpResponseCode.RESULT_NOT_FOUND);
        }
        Long lectureCode = lecture.get().getLectureCode();

        Optional<Student> student = studentRepository.findByEmail(registerReq.getEmail());
        if (!student.isPresent()) {
            throw new TestException(TestHttpResponseCode.RESULT_NOT_FOUND);
        }
        Long studentNum = student.get().getStudentNum();

        //검색한 결과가 있다면, 해당 레코드의 취소 상태를 업데이트
        Optional<Register> register = registerRepository.findByLecture_LectureCodeAndStudent_StudentNum(lectureCode, studentNum);
        if (!register.isPresent()){
            throw new TestException(TestHttpResponseCode.RESULT_NOT_FOUND);
        }

        //이미 취소를 한 경우,
        if (register.get().getCancelStatus().equals("YES")) {
            throw new TestException(TestHttpResponseCode.DUPLICATE_REQUEST);
        }

        register.get().setCancelStatus("YES");
        return new ResponseEntity<RegisterDto>(RegisterDto.builder()
                .registerId(register.get().getRegisterId())
                .cancelStatus(register.get().getCancelStatus())
                .lectureCode(register.get().getLecture().getLectureCode())
                .studentNum(register.get().getStudent().getStudentNum())
                .build(), HttpStatus.CREATED);
    }

    //수강신청 리스트
    public ResponseEntity<Page<RegisterDto>> registerList(String email, int page, int size, String sort) {

        Optional<Student> student = studentRepository.findByEmail(email);
        if (!student.isPresent()) {
            throw new TestException(TestHttpResponseCode.RESULT_NOT_FOUND);
        }
        Page<Register> registerList = registerRepository.findAllByStudent_StudentNumIs(student.get().getStudentNum(), PageRequest.of(page, size, Sort.Direction.ASC, sort));
        if (registerList.isEmpty()){
            throw new TestException(TestHttpResponseCode.RESULT_NOT_FOUND);
        }
        Page<RegisterDto> registerDtos = registerList.map(RegisterDto::new);
        return new ResponseEntity<>(registerDtos, HttpStatus.OK);
    }

    //나를 추천한 강사 조회
    public ResponseEntity<String> recommendedMe(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalStateException();
        }
        Optional<Student> result = studentRepository.findByEmail(email);
        if (!result.isPresent()) {
            throw new TestException(TestHttpResponseCode.RESULT_NOT_FOUND);
        }
        if (result.get().getJoinStatus().equals("NO")) {
            throw new TestException(TestHttpResponseCode.RESULT_NOT_FOUND);
        }
        return new ResponseEntity<>(teacherRepository.findById(result.get().getTeacher().getTeacherNum()).get().getTeacherName(), HttpStatus.OK);
    }
}
