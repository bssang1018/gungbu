package com.kona.soogang.service;

import com.kona.soogang.aop.LoginCheck;
import com.kona.soogang.domain.*;
import com.kona.soogang.dto.RegisterDto;
import com.kona.soogang.dto.StudentDto;
import com.kona.soogang.dto.TeacherDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class StudentService {


    private final StudentRepository studentRepository;
    private final LectureRepository lectureRepository;
    private final RegisterRepository registerRepository;

    public StudentService(StudentRepository studentRepository, LectureRepository lectureRepository, RegisterRepository registerRepository) {
        this.studentRepository = studentRepository;
        this.lectureRepository = lectureRepository;
        this.registerRepository = registerRepository;
    }

    //학생 회원가입
//    @Transactional
//    public String studentJoin(String email, String pw, String name) {
//
//        Student student = new Student();
//        student.setEmail(email);
//        student.setPw(pw);
//        student.setName(name);
//
//        //이메일 중복 체크
//        validateDuplicateCheck(email);
//
//        //강사의 추천을 확인
//        String recommendResult = recommendCheck(email);
//        System.out.println("추천여부 확인 결과 :: "+recommendResult);
//
//        student.setJoinStatus(recommendResult);
//
//        studentRepository.save(student);
//
//        String joinMent = email + "님! 가입이 되었습니다.";
//        return joinMent;
//    }
//    @Transactional(rollbackFor = Exception.class)
//    public String studentJoin(HashMap<String, String> param) {
//
//        String email = param.get("email");
//        String pw = param.get("pw");
//        String name = param.get("name");
//
//        Student student = new Student();
//        student.setEmail(email);
//        student.setPw(pw);
//        student.setName(name);
//
//        //이메일 중복 체크
//        validateDuplicateCheck(email);
//
//        //강사의 추천을 확인
//        String recommendResult = recommendCheck(email);
//        System.out.println("추천여부 확인 결과 :: " + recommendResult);
//
//        student.setJoinStatus(recommendResult);
//
//        studentRepository.save(student);
//
//        String joinMent = email + "님! 가입이 되었습니다.";
//        return joinMent;
//    }
    @Transactional(rollbackFor = Exception.class)
    public String studentJoin(StudentDto studentDto) {

        //이메일 중복 체크
        validateDuplicateCheck(studentDto.getEmail());
        //강사의 추천을 확인
        String recommendResult = recommendCheck(studentDto.getEmail());
        System.out.println("추천여부 확인 결과 :: " + recommendResult);//NO 또는 BY

//        if (recommendResult.equals("BY")){
//            Student studentA = studentRepository.findById(studentDto.getId()).get();
//            studentA.joinStatusUpdate();
//        }
        studentDto.setJoinStatus(recommendResult);
        studentRepository.save(studentDto.toEntity());

        return studentDto.getEmail()+"님! 가입이 되었습니다.";
    }

    //이메일 중복 체크
    public void validateDuplicateCheck(String email) {
        Optional<Student> duplicateResult = studentRepository.findById(email);

        if (duplicateResult.isPresent() && !duplicateResult.get().getJoinStatus().equals("BN")) { //조회 결과가 유효하면서 AY,NO,BY 면 예외. 중복 이메일이라는 뜻
            throw new IllegalStateException("INFO :: already exist email");
        }
    }

    //학생 추천 여부 체크
    public String recommendCheck(String email) {

        if (!studentRepository.findById(email).isPresent()) { //중복없으면 따질것도 없이 신규 가입
            return "NO";
        }

        Student student = studentRepository.findById(email).get();
        if (student.getEmail() == email && student.getJoinStatus() == "BN") { //BN은 사전에 추천받았지만, 미가입상태
            return "BY"; //가입 상태로 업데이트
        } else {
            return "NO"; //사전 추천 받지 못한경우, NO
        }
    }

    //    public String studentLogin(String email, String pw, HttpSession session) {
//        List<Student> loginResult = studentRepository.findByEmailAndPw(email, pw);
//        if (loginResult.isEmpty()){
//            throw new IllegalStateException("INFO :: login fail!");
//        }
//        session.setAttribute("loginId", email);
//
//        String loginMent = email + "님! 로그인 됐습니다!";
//        return loginMent;
//    }
    public String studentLogin(StudentDto studentDto, HttpSession session) {
        String email = studentDto.getEmail();
        String pw = studentDto.getPw();

        List<Student> loginResult = studentRepository.findByEmailAndPw(email, pw);
        if (loginResult.isEmpty()) {
            throw new IllegalStateException("INFO :: login fail!");
        }
        session.setAttribute("loginId", email);

        String loginMent = email + "님! 로그인 됐습니다!";
        return loginMent;
    }

    //나를 추천한 강사 조회
    @Transactional(rollbackFor = Exception.class)
    public StringBuffer recommendedMe(String email) {
        Optional<Student> result = studentRepository.findById(email);
        StringBuffer sb = new StringBuffer();

        if (result.get().getJoinStatus().equals("NO")) {
            sb.append("추천을 받지 못했습니다.");
            return sb;
        }

        sb.append("나를 추천한 강사의 아이디는 ");
        sb.append(result.get().getTeacher().getId());
        sb.append(" 입니다.\r\n");
        sb.append("강사의 이름은 ");
        sb.append(result.get().getTeacher().getName());
        sb.append(" 입니다.");
        return sb;
    }

//    //수강신청
//    @LoginCheck
//    @Transactional
//    public String lectureRegister(String lectureName,HttpSession session) {
//        String email = (String) session.getAttribute("loginId");
//
//        //1. 수용인원과 추천여부 따져보기
//        List<Lecture> lecture = lectureRepository.findByLectureName(lectureName);
//        //최대 인원
//        int maxPerson = lecture.get(0).getMaxPerson();
//        System.out.println("@@@@@@ maxPerson:: "+ maxPerson);
//        //강의명으로 현재 신청 인원수 파악
//        //강의 테이블에서 강의명으로 코드 조회
//        Long lectureCode = lecture.get(0).getLectureCode();
//        int registersCount = registerRepository.countRegistered(lectureCode);
//        System.out.println("@@@@@@ registersCount:: " + registersCount);
//        //현재 로그인한 학생의 추천 여부 확인
//        String joinStatus = studentRepository.findById(email).get().getJoinStatus();
//        System.out.println("joinStatus:: "+joinStatus);
//
//        if (registersCount >= maxPerson && joinStatus.equals("NO")){ //인원이 맥스 이상이고, 추천학생이 아닌 경우 팅궈 => 둘다 만족해야 팅궈
//            throw new IllegalStateException("INFO:: this lecture is already full");
//        }
//        //인원을 초과히지 않는 경우는 정상 진행
//        //추천받은 학생인 경우 정상 진행 ("BN","AY")
//
//        //2. 중복신청 체크
//        RegisterId registerId = new RegisterId();
//        registerId.setLectureCode(lectureCode);
//        registerId.setStudentEmail(email);
//        List<Register> registerForDuplicate = registerRepository.findByRegisterId(registerId);
//
//        Register register = new Register();
//        register.setRegisterId(registerId);
//        register.setCancelStatus("NO");
//
//        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//        register.setTimestamp(timestamp);
//
//        //중복이지만, 신청을 취소했던 경우 재신청
//        if (!registerForDuplicate.isEmpty() && registerForDuplicate.get(0).getCancelStatus().equals("YES")){
//            registerRepository.save(register);
//            return '"'+lectureName+'"'+ " 강의를 다시 수강 신청 했습니다.";
//        }
//
//        //그냥 중복인 경우
//        if (!registerForDuplicate.isEmpty()){
//            throw new IllegalStateException("INFO:: you have already registered to this lecture");
//        }
//
//        //모든 경우에 해당하지 않는다면 정상적으로 수강 신청
//        registerRepository.save(register);
//        String registerMent = '"'+lectureName+'"'+ " 강의를 수강 신청 했습니다.";
//        return registerMent;
//    }

    //수강신청
    @Transactional(rollbackFor = Exception.class)
    public String lectureRegister(RegisterDto registerDto) {

        //1. 수용인원과 추천여부 따져보기
        Lecture lecture = lectureRepository.findById(registerDto.getLectureCode()).get();

        //최대 인원
        int maxPerson = lecture.getMaxPerson();
        System.out.println("@@@@@@ maxPerson:: " + maxPerson);
        //강의명으로 현재 신청 인원수 파악
        int registersCount = registerRepository.countRegistered(registerDto.getLectureCode());
        System.out.println("@@@@@@ registersCount:: " + registersCount);
        //현재 로그인한 학생의 추천 여부 확인
        String joinStatus = studentRepository.findById(registerDto.getEmail()).get().getJoinStatus();
        System.out.println("joinStatus:: " + joinStatus);

        if (registersCount >= maxPerson && joinStatus.equals("NO")) { //인원이 맥스 이상이고, 추천학생이 아닌 경우 팅궈 => 둘다 만족해야 팅궈
            throw new IllegalStateException("INFO:: this lecture is already full");
        }
        //인원을 초과히지 않는 경우는 정상 진행
        //추천받은 학생인 경우 정상 진행 ("BN","AY")

        //2. 중복신청 체크
        RegisterId registerId = new RegisterId();
        registerId.setLectureCode(registerDto.getLectureCode());
        registerId.setEmail(registerDto.getEmail());
        List<Register> registerForDuplicate = registerRepository.findByRegisterId(registerId);

        Register register = new Register();
        register.setRegisterId(registerId);
        register.setCancelStatus("NO");

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        register.setTimestamp(timestamp);

        //강의명을 가져오자
        String lectureName = lectureRepository.findById(registerDto.getLectureCode()).get().getLectureName();

        //중복이지만, 신청을 취소했던 경우 재신청
        if (!registerForDuplicate.isEmpty() && registerForDuplicate.get(0).getCancelStatus().equals("YES")) {
            registerRepository.save(register);
            return '"' + lectureName + '"' + " 강의를 다시 수강 신청 했습니다.";
        }

        //그냥 중복인 경우
        if (!registerForDuplicate.isEmpty()) {
            throw new IllegalStateException("INFO:: you have already registered to this lecture");
        }

        //모든 경우에 해당하지 않는다면 정상적으로 수강 신청
        registerRepository.save(register);
        String registerMent = '"' + lectureName + '"' + " 강의를 수강 신청 했습니다.";
        return registerMent;
    }

//    @LoginCheck
//    @Transactional
//    public String registerCancel(String lectureName, HttpSession session) {
//        String email = (String) session.getAttribute("loginId");
//
//        Long lectureCode = lectureRepository.findByLectureName(lectureName).get(0).getLectureCode();
//
//        RegisterId registerId = new RegisterId();
//        registerId.setLectureCode(lectureCode);
//        registerId.setStudentEmail(email);
//
//        if(registerRepository.findByRegisterId(registerId).get(0).getCancelStatus().equals("YES")){
//            return "이미 취소한 강의입니다.";
//        }
//
//        registerRepository.registerCancel(lectureCode, email);
//
//        String cancelMent = '"'+lectureName+'"'+ " 강의 수강을 취소했습니다.";
//        return cancelMent;
//    }

    @Transactional(rollbackFor = Exception.class)
    public String registerCancel(RegisterDto registerDto) {
        String email = registerDto.getEmail();
        Long lectureCode = registerDto.getLectureCode();

        RegisterId registerId = new RegisterId();
        registerId.setLectureCode(lectureCode);
        registerId.setEmail(email);
        if (registerRepository.findByRegisterId(registerId).get(0).getCancelStatus().equals("YES")) {
            return "이미 취소한 강의입니다.";
        }

        registerRepository.registerCancel(lectureCode, email);

        return "강의 수강을 취소했습니다.(강의코드: "+lectureCode+")";
    }

    public List<RegisterDto> registerList(HttpSession session) {
        String email = (String) session.getAttribute("loginId");

        List<Register> registerList = registerRepository.findByStudentEmail(email);

        return registerList.stream().map(RegisterDto::new).collect(Collectors.toList());
    }
}
