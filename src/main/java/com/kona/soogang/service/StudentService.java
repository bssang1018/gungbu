package com.kona.soogang.service;

import com.kona.soogang.domain.*;
import com.kona.soogang.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<StudentDto> studentJoin(StudentReq studentReq) {

        if (studentReq.getEmail() == null || studentReq.getName() == null) {
            throw new IllegalStateException();
        }

        Optional<Student> student = studentRepository.findByEmail(studentReq.getEmail());
        if (!student.isPresent()) {
            //중복의 결과가 없다면 신규로 회원가입 진행
            System.out.println("신규가입");
            Student student1 = new Student()
                    .builder()
                    .email(studentReq.getEmail())
                    .name(studentReq.getName())
                    .joinStatus("NO")
                    .build();
            studentRepository.save(student1);

            return new ResponseEntity<>(new StudentDto().builder()
                    .studentNum(student1.getStudentNum())
                    .email(student1.getEmail())
                    .name(student1.getName())
                    .joinStatus(student1.getJoinStatus())
                    .build()
                    , HttpStatus.CREATED);
        }

        if (student.isPresent() && student.get().getJoinStatus().equals("BN")) {
            //중복결과가 존재하면서, 선생의 추천으로 이메일만 이미 디비에 등록된 경우
            //해당 레코드의 상태를 업데이트
            student.get().studentUpdate(studentReq.getName(), "BY");
            //업데이트 하고 해당 레코드 조회해서 가져오기
            Student student1 = studentRepository.findByEmail(studentReq.getEmail()).get();
            return new ResponseEntity<>(new StudentDto().builder()
                    .studentNum(student1.getStudentNum())
                    .email(student1.getEmail())
                    .joinStatus(student1.getJoinStatus())
                    .teacherNum(student1.getTeacher().getTeacherNum())
                    .build()
                    , HttpStatus.CREATED);
        } else {
            //중복결과가 존재면면서, 상태가 NO, AY, BY면
            //단순 이메일 중복으로 인한 가입 거절
            throw new IllegalArgumentException();
        }
    }

    //수강신청
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<RegisterDto> lectureRegister(RegisterReq registerReq) {
        //강의명과 email로 해당 키값 가져오기
        Optional<Lecture> lecture = lectureRepository.findByLectureName(registerReq.getLectureName());
        if (!lecture.isPresent()) {
            throw new IllegalStateException();
        }
        Long lectureCode = lecture.get().getLectureCode();

        Optional<Student> student = studentRepository.findByEmail(registerReq.getEmail());
        if (!student.isPresent()) {
            throw new IllegalStateException();
        }
        Long studentNum = student.get().getStudentNum();
        int maxPerson = lecture.get().getMaxPerson();

        //신청인원이 초과한 경우, 수강신청 불가능
        //lectureCode로 카운트
        if (registerRepository.findAllByLectureCode(lectureCode) >= maxPerson) {

            if (registerRepository.findByLecture_LectureCodeAndStudent_StudentNum(lectureCode, studentNum).isPresent()) {
                throw new IllegalArgumentException();
            }
            //수용인원을 초과하고, 학생의 추천상태가 AY또는 BY인 경우에는 수강신청 프리패스
            if (!studentRepository.findById(studentNum).get().getJoinStatus().equals("NO")) {
                RegisterDto registerDto = new RegisterDto();
                registerDto.setLectureCode(lectureCode);
                registerDto.setStudentNum(studentNum);
                registerDto.setCancelStatus("NO");
                Register registerResult =  registerRepository.save(registerDto.toEntity());
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
            throw new ClassCastException();
        }

        //이미 수강신청을 한 강의의 경우, 중복신청 불가능
        //lectureCode와 studentNum 이 둘다 겹치면 중복으로 판단
        if (registerRepository.findByLecture_LectureCodeAndStudent_StudentNum(lectureCode, studentNum).isPresent()) {
            throw new IllegalArgumentException();
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
            throw new IllegalStateException();
        }
        Long lectureCode = lecture.get().getLectureCode();
        Optional<Student> student = studentRepository.findByEmail(registerReq.getEmail());
        if (!student.isPresent()) {
            throw new IllegalStateException();
        }
        Long studentNum = student.get().getStudentNum();

        //검색한 결과가 있다면, 해당 레코드의 취소 상태를 업데이트
        Optional<Register> register = registerRepository.findByLecture_LectureCodeAndStudent_StudentNum(lectureCode, studentNum);

        //이미 취소를 한 경우,
        if (register.get().getCancelStatus().equals("YES")) {
            throw new IllegalArgumentException();
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
    public List<RegisterResponse> registerList(String email, Pageable pageable) {
        Optional<Student> student = studentRepository.findByEmail(email);
        if (!student.isPresent()) {
            //throw new IllegalStateException("INFO:: 존재하지 않는 이메일 입니다. 이메일을 확인해주세요.");
            throw new IllegalStateException();
        }
        Page<Register> registerList = registerRepository.findAllByStudent_StudentNum(student.get().getStudentNum(), pageable);
        return registerList.stream().map(RegisterResponse::new).collect(Collectors.toList());
    }

    //나를 추천한 강사 조회
    public String recommendedMe(String email) {
        Optional<Student> result = studentRepository.findByEmail(email);
        if (!result.isPresent()) {
            //return "해당 이메일은 존재하지 않습니다. 이메일을 확인해주세요.";
            throw new IllegalStateException();
        }
        if (result.get().getJoinStatus().equals("NO")) {
            return "어떤 강사에게도 추천을 받지 못했습니다.";
        }
        return email + " 을 추천한 강사는 " + teacherRepository.findById(result.get().getTeacher().getTeacherNum()).get().getTeacherName() + " 입니다.";
    }
}
