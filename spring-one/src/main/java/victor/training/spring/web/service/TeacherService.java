package victor.training.spring.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import victor.training.spring.web.controller.dto.TeacherDto;
import victor.training.spring.web.domain.Teacher;
import victor.training.spring.web.repo.TeacherRepo;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherService {
    @Autowired
    private TeacherRepo teacherRepo;

    public List<TeacherDto> getAllTeachers() {
        return teacherRepo.findAll().stream().map(TeacherDto::new).collect(Collectors.toList());
    }

    public void createTeacher(TeacherDto dto) {
        if (StringUtils.isEmpty(dto.name)) {
            throw new IllegalArgumentException("Empty teacher name");
        }
        teacherRepo.save(new Teacher(dto.name));
    }

    public void deleteTeacher(Long id) {
        teacherRepo.deleteById(id);
    }

    @Async
    public void process(File serializable) {
        try {
            // rasnesti la fisier 20 min
//            parse file
//                inserezi in db
        } catch (Exception e) {
//            La eroare inserezi in tabela ERORI > REQUIRES_NEW
        }
    }
}
