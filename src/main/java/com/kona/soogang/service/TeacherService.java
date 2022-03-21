package com.kona.soogang.service;

import com.kona.soogang.domain.*;
import com.kona.soogang.dto.LectureDto;
import com.kona.soogang.dto.StudentDto;
import com.kona.soogang.dto.TeacherDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final LectureRepository lectureRepository;
    private final RegisterRepository registerRepository;

    public TeacherService(TeacherRepository teacherRepository, StudentRepository studentRepository, LectureRepository lectureRepository, RegisterRepository registerRepository) {
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.lectureRepository = lectureRepository;
        this.registerRepository = registerRepository;
    }

    // 강사 회원가입
    @Transactional(rollbackFor = Exception.class)
    public String teacherJoin(TeacherDto teacherDto) {
        Teacher teacher = teacherDto.toEntity();
        // id 중복검사
        validateDuplicate(teacherDto);
        teacherRepository.save(teacher);
        return teacherDto.getTeacherId() + " 님의 가입을 완료 했습니다!";
    }
    // id 중복검사 메서드
    private void validateDuplicate(TeacherDto teacherDto) {
        Optional<Teacher> duplicateResult = teacherRepository.findByTeacherId(teacherDto.getTeacherId());
        if (duplicateResult.isPresent()) { //true면 중복
            throw new RuntimeException("INFO :: please try again to join by another id");
        }
    }

    //학생 추천
    @Transactional(rollbackFor = Exception.class)
    public String recommend(StudentDto studentDto) {
        System.out.println("추천 파라미터 확인 :: " + studentDto.getTeacherNum() + " / " + studentDto.getEmail());
        Optional<Student> student = studentRepository.findByEmail(studentDto.getEmail());

        if (!student.isPresent()){
            //등록하려는 학생이 테이블에 없는 경우 새로 저장
            studentDto.setJoinStatus("BN");//BN:가입전에 추천받고, 미가입상태
            studentRepository.save(studentDto.toEntity());
            return studentDto.getEmail()+" 학생을 추천 했습니다.";
        }
        //이미 등록되어 있는 학생이고, 다른 강사의 추천을 이미 받은 경우(BN이나 AY인 상태)
        if(student.get().getJoinStatus().equals("BN") || student.get().getJoinStatus().equals("AY")){
            return "이미 추천을 받은 학생이므로, 추천을 할 수 없습니다.";
        }else{
            //BY도 아니고, AY도 아닌 NO인 상태인 경우
            //해당 컬럼을 업데이트
            student.get().setTeacher(Teacher.builder().teacherNum(studentDto.getTeacherNum()).build());
            student.get().setJoinStatus("AY");
            return studentDto.getEmail()+" 학생을 추천 했습니다.";
        }
    }
    
    //강의 등록
    @Transactional(rollbackFor = Exception.class)
    public String lectureInsert(LectureDto lectureDto) {

        if (lectureDto.getMaxPerson() > 100) {
            //throw new IllegalStateException("INFO:: the maximum number of participants is 100.");
            return "최대인원은 최대 100명까지만 설정할 수 있습니다. 인원수를 조정해 주세요.";
        }
        validateDuplicateLecture(lectureDto.getLectureName());
        lectureRepository.save(lectureDto.toEntity());
        return "강의를 등록했습니다.(강의명: "+lectureDto.getLectureName()+")";
    }
    private void validateDuplicateLecture(String lectureName) {
        Optional<Lecture> LectureDuplicateResult = lectureRepository.findByLectureName(lectureName);
        //같은 이름의 강의는 등록할 수 없다
        if (LectureDuplicateResult.isPresent()) {
            throw new RuntimeException("INFO :: already exist lecture name");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public String lectureClose(LectureDto lectureDto) {
        Optional<Lecture> lecture = lectureRepository.findByLectureName(lectureDto.getLectureName());
        if (!lecture.isPresent()){
            //return "해당 강의를 찾을 수 없습니다. 강의명을 확인해 주세요";
            throw new IllegalStateException();
        }

        Long lectureCode = lecture.get().getLectureCode();
        int countRegistered = registerRepository.findAllByLectureCode(lectureCode);
        if (countRegistered > 2) { //3명 부터 폐강 불가능
            return "수강을 신청한 학생이 2명 이상입니다. 폐강할 수 없습니다.";
        }
        if (lecture.get().getCloseStatus().equals("YES")) {
            return "이미 폐강 처리가 완료된 강의 입니다.";
        }
        
        //폐강 상태만 업데이트 처리
        lecture.get().setCloseStatus("YES");
        return lectureDto.getLectureName()+" 강의를 폐강했습니다.";
    }

    //추천된 학생 조회
    public List<StudentDto> recommendedStudentList(Pageable pageable) {
        String[] arr = {"BY","BN"};
        Page<Student> studentList = studentRepository.findAllByJoinStatusIn(arr, pageable);

        if(studentList.isEmpty()){
            throw new IllegalStateException();
        }

        return studentList.stream().map(StudentDto::new).collect(Collectors.toList());
    }
}



