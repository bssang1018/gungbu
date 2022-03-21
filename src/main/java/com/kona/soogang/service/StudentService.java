package com.kona.soogang.service;

import com.kona.soogang.domain.*;
import com.kona.soogang.dto.RegisterDto;
import com.kona.soogang.dto.RegisterResponse;
import com.kona.soogang.dto.StudentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public String studentJoin(StudentDto studentDto) {
        Optional<Student> student = studentRepository.findByEmail(studentDto.getEmail());
        if (!student.isPresent()) {
            //중복의 결과가 없다면 신규로 회원가입 진행
            Student student1 = new Student()
                    .builder()
                    .email(studentDto.getEmail())
                    .name(studentDto.getName())
                    .joinStatus("NO")
                    .build();
            studentRepository.save(student1);
            return studentDto.getEmail() + " 님의 신규가입이 정상적으로 처리되었습니다";
        }
        if (student.isPresent() && student.get().getJoinStatus().equals("BN")) {
            //중복결과가 존재하면서, 선생의 추천으로 이메일만 이미 디비에 등록된 경우
            //해당 레코드의 상태를 업데이트
            student.get().studentUpdate(studentDto.getName(), "BY");
            return "추천을 받은 상태로 가입이 완료되었습니다.";
        } else {
            //중복결과가 존재면면서, 상태가 NO, AY, BY면
            //단순 이메일 중복으로 인한 가입 거절
            return "다른 이메일로 가입을 시도해주세요.";
            //throw new IllegalStateException();
        }
    }

    //수강신청
    @Transactional(rollbackFor = Exception.class)
    public String lectureRegister(RegisterDto registerDto) {
        //강의명과 email로 해당 키값 가져오기
        Optional<Lecture> lecture = lectureRepository.findByLectureName(registerDto.getLectureName());
        if (!lecture.isPresent()) {
            //return "존재하지 않는 강의입니다. 강의명을 확인해 주세요";
            throw new IllegalStateException();
        }
        Long lectureCode = lecture.get().getLectureCode();

        Optional<Student> student = studentRepository.findByEmail(registerDto.getEmail());
        if (!student.isPresent()) {
            //return "존재하지 않는 이메일입니다. 이메일을 확인해 주세요";
            throw new IllegalStateException();
        }
        Long studentNum = student.get().getStudentNum();
        int maxPerson = lecture.get().getMaxPerson();

        //신청인원이 초과한 경우, 수강신청 불가능
        //lectureCode로 카운트
        if (registerRepository.findAllByLectureCode(lectureCode) >= maxPerson) {

            if(registerRepository.findByLecture_LectureCodeAndStudent_StudentNum(lectureCode, studentNum).isPresent()){
                return "중복신청은 불가능합니다.";
                //throw new IllegalStateException();
            }
            //수용인원을 초과하고, 학생의 추천상태가 AY또는 BY인 경우에는 수강신청 프리패스
            if (!studentRepository.findById(studentNum).get().getJoinStatus().equals("NO")) {
                registerDto.setLectureCode(lectureCode);
                registerDto.setStudentNum(studentNum);
                registerDto.setCancelStatus("NO");
                registerRepository.save(registerDto.toEntity());
                return "강사의 추천을 받았으므로, 신청인원과 상관없이 수강신청을 완료했습니다.";
            }
            return "해당 강의는 신청인원을 초과했습니다. 죄송합니다, 다른 강의를 수강신청 해주세요.(중복신청 또한 불가능 합니다.)";
        }

        //이미 수강신청을 한 강의의 경우, 중복신청 불가능
        //lectureCode와 studentNum 이 둘다 겹치면 중복으로 판단
        if (registerRepository.findByLecture_LectureCodeAndStudent_StudentNum(lectureCode, studentNum).isPresent()) {
            return "이미 수강신청을 한 강의입니다. 중복하여 신청은 불가능 합니다.";
            //throw new IllegalStateException();
        }

        //수강 신청
        registerDto.setLectureCode(lectureCode);
        registerDto.setStudentNum(studentNum);
        registerDto.setCancelStatus("NO");
        registerRepository.save(registerDto.toEntity());
        return registerDto.getLectureName() + " 강의를 수강신청 했습니다.";
    }

    //수강신청 취소
    @Transactional(rollbackFor = Exception.class)
    public String registerCancel(RegisterDto registerDto) {
        Optional<Lecture> lecture = lectureRepository.findByLectureName(registerDto.getLectureName());
        if (!lecture.isPresent()) {
            //return "존재하지 않는 강의입니다. 강의명을 확인해 주세요";
            throw new IllegalStateException();
        }
        Long lectureCode = lecture.get().getLectureCode();
        Optional<Student> student = studentRepository.findByEmail(registerDto.getEmail());
        if (!student.isPresent()) {
            //return "존재하지 않는 이메일입니다. 이메일을 확인해 주세요";
            throw new IllegalStateException();
        }
        Long studentNum = student.get().getStudentNum();

        //검색한 결과가 있다면, 해당 레코드의 취소 상태를 업데이트
        Optional<Register> register = registerRepository.findByLecture_LectureCodeAndStudent_StudentNum(lectureCode, studentNum);

        //이미 취소를 한 경우,
        if(register.get().getCancelStatus().equals("YES")){
            return "이미 수강신청을 취소한 강의입니다.";
            //throw new IllegalStateException();
        }

        register.get().setCancelStatus("YES");
        return registerDto.getLectureName() + " 강의 수강신청을 취소했습니다.";
    }

    //수강신청 리스트
    public List<RegisterResponse> registerList(String email, Pageable pageable) {
        Optional<Student> student = studentRepository.findByEmail(email);
        if (!student.isPresent()){
            //throw new IllegalStateException("INFO:: 존재하지 않는 이메일 입니다. 이메일을 확인해주세요.");
            throw new IllegalStateException();
        }
        Page<Register> registerList = registerRepository.findAllByStudent_StudentNum(student.get().getStudentNum(), pageable);
        return registerList.stream().map(RegisterResponse::new).collect(Collectors.toList());
    }

    //나를 추천한 강사 조회
    public String recommendedMe(String email) {
        Optional<Student> result = studentRepository.findByEmail(email);
        if (!result.isPresent()){
            //return "해당 이메일은 존재하지 않습니다. 이메일을 확인해주세요.";
            throw new IllegalStateException();
        }
        if (result.get().getJoinStatus().equals("NO")) {
            return "어떤 강사에게도 추천을 받지 못했습니다.";
        }
        return email+" 을 추천한 강사는 " + teacherRepository.findById(result.get().getTeacher().getTeacherNum()).get().getTeacherName() + " 입니다.";
    }
}
