package victor.training.spring.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import victor.training.spring.web.controller.dto.TeacherDto;
import victor.training.spring.web.entity.Teacher;
import victor.training.spring.web.repo.TeacherRepo;

@Service
public class TeacherService {
    @Autowired
    private TeacherRepo teacherRepo;

    // TODO Cacheable for list-of-all
    @Cacheable("all-teachers") // please imagine this defines a Map<Nothing, List<TeacherDto>> allTeachers;
    public Flux<TeacherDto> getAllTeachers() {
        return teacherRepo.findAll().map(TeacherDto::new);
    }

    public Mono<Teacher> createTeacher(TeacherDto dto) {
        return teacherRepo.save(new Teacher(dto.name));
    }

    public void updateTeacher(long id, String newName) {
        // TODO victorrentea 2022-09-28: reactive
//        Mono<Teacher> teacher = teacherRepo.findById(id);
//        teacher.setName(newName);
//        teacherRepo.save(teacher);
    }
}
