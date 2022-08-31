package victor.training.spring.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import victor.training.spring.aspects.Facade;
import victor.training.spring.web.controller.dto.TeacherDto;
import victor.training.spring.web.entity.ContractType;
import victor.training.spring.web.entity.Teacher;
import victor.training.spring.web.repo.TeacherRepo;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class TeacherService {
    @Autowired
    private TeacherRepo teacherRepo;

    public List<TeacherDto> getAllTeachers() {
//        new RuntimeException().printStackTrace();
        return teacherRepo.findAll().stream().map(TeacherDto::new).collect(toList());
    }

    public void createTeacher(TeacherDto dto) {
        teacherRepo.save(new Teacher(dto.name));
    }

    public void deleteTeacher(Long id) {
        teacherRepo.deleteById(id);
    }

    @Transactional
    public void updateTeacher(long id, String newName) {
        Teacher teacher = teacherRepo.findById(id).orElseThrow();
        teacher.setName(newName);
    }
}
