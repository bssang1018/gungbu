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
    public ResponseEntity<TeacherDto> teacherJoin(TeacherReq teacherReq) {

        if (teacherReq.getTeacherId() == "" || teacherReq.getTeacherName() == ""){
            throw new NullPointerException();
        }
        //id 중복 검사
        validateDuplicate(teacherReq.getTeacherId());

        Teacher teacher = new Teacher().builder()
                .teacherId(teacherReq.getTeacherId())
                .teacherName(teacherReq.getTeacherName())
                .build();

        Teacher result = teacherRepository.save(teacher);
        return new ResponseEntity<>(new TeacherDto().builder()
                .teacherNum(result.getTeacherNum())
                .teacherId(result.getTeacherId())
                .teacherName(result.getTeacherName())
                .build(), HttpStatus.CREATED);
    }
    // id 중복검사 메서드
    private void validateDuplicate(String teacherId) {
        Optional<Teacher> duplicateResult = teacherRepository.findByTeacherId(teacherId);
        if (duplicateResult.isPresent()) { //true면 중복
            throw new IllegalArgumentException();
        }
    }

    //학생 추천
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<StudentDto> recommend(RecommendReq recommendReq) {

        Optional<Student> student = studentRepository.findByEmail(recommendReq.getEmail());
        Optional<Teacher> teacher = teacherRepository.findByTeacherId(recommendReq.getTeacherId());

        if (!teacher.isPresent()){
            throw new IllegalStateException();
        }

        if (!student.isPresent()){
            StudentDto studentDto = new StudentDto();
            //등록하려는 학생이 테이블에 없는 경우 새로 저장
            studentDto.setEmail(recommendReq.getEmail());
            studentDto.setJoinStatus("BN");//BN:가입전에 추천받고, 미가입상태
            studentDto.setTeacherNum(teacher.get().getTeacherNum());
            Student student1 = studentRepository.save(studentDto.toEntity());
            return new ResponseEntity<>(StudentDto.builder()
                    .studentNum(student1.getStudentNum())
                    .email(student1.getEmail())
                    .name(student1.getName())
                    .joinStatus(student1.getJoinStatus())
                    .teacherNum(student1.getTeacher().getTeacherNum())
                    .build(), HttpStatus.CREATED);
        }
        //이미 등록되어 있는 학생이고, 다른 강사의 추천을 이미 받은 경우(BN이나 AY인 상태)
        if(student.get().getJoinStatus().equals("BN") || student.get().getJoinStatus().equals("AY")){
            throw new IllegalArgumentException();
        }else{
            //BY도 아니고, AY도 아닌 NO인 상태인 경우
            //해당 컬럼을 업데이트
            student.get().setTeacher(Teacher.builder().teacherNum(teacher.get().getTeacherNum()).build());
            student.get().setJoinStatus("AY");
            return new ResponseEntity<>(StudentDto.builder()
                    .studentNum(student.get().getStudentNum())
                    .email(student.get().getEmail())
                    .name(student.get().getName())
                    .joinStatus(student.get().getJoinStatus())
                    .teacherNum(student.get().getTeacher().getTeacherNum())
                    .build(), HttpStatus.CREATED);
        }
    }
    
    //강의 등록
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<LectureDto> lectureInsert(LectureReq lectureReq) {

        if (lectureReq.getMaxPerson() > 100) {
            throw new ClassCastException();
        }
        Optional<Teacher> teacher = teacherRepository.findByTeacherId(lectureReq.getTeacherId());
        if (!teacher.isPresent()){
            throw new IllegalStateException();
        }
        validateDuplicateLecture(lectureReq.getLectureName());

        LectureDto lectureDto = new LectureDto().builder()
                .lectureName(lectureReq.getLectureName())
                .closeStatus("NO")
                .maxPerson(lectureReq.getMaxPerson())
                .teacherNum(teacher.get().getTeacherNum())
                .build();
        Lecture lecture = lectureRepository.save(lectureDto.toEntity());
        return new ResponseEntity<>(LectureDto.builder()
                .lectureCode(lecture.getLectureCode())
                .lectureName(lecture.getLectureName())
                .teacherNum(lecture.getTeacher().getTeacherNum())
                .maxPerson(lecture.getMaxPerson())
                .closeStatus(lecture.getCloseStatus())
                .build(), HttpStatus.CREATED);
    }
    private void validateDuplicateLecture(String lectureName) {
        Optional<Lecture> LectureDuplicateResult = lectureRepository.findByLectureName(lectureName);
        //같은 이름의 강의는 등록할 수 없다
        if (LectureDuplicateResult.isPresent()) {
            throw new IllegalArgumentException();
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<LectureDto> lectureClose(CloseReq closeReq) {
        Optional<Lecture> lecture = lectureRepository.findByLectureName(closeReq.getLectureName());
        if (!lecture.isPresent()){
            //return "해당 강의를 찾을 수 없습니다. 강의명을 확인해 주세요";
            throw new IllegalStateException();
        }

        Long lectureCode = lecture.get().getLectureCode();
        int countRegistered = registerRepository.findAllByLectureCode(lectureCode);
        if (countRegistered > 2) { //3명 부터 폐강 불가능
            throw new ClassCastException();
        }
        if (lecture.get().getCloseStatus().equals("YES")) {
            throw new IllegalArgumentException();
        }
        
        //폐강 상태만 업데이트 처리
        lecture.get().setCloseStatus("YES");
        return new ResponseEntity<>(LectureDto.builder()
                .lectureCode(lecture.get().getLectureCode())
                .lectureName(lecture.get().getLectureName())
                .teacherNum(lecture.get().getTeacher().getTeacherNum())
                .maxPerson(lecture.get().getMaxPerson())
                .closeStatus(lecture.get().getCloseStatus())
                .build(), HttpStatus.CREATED);
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



