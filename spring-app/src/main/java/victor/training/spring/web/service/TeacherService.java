package victor.training.spring.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import victor.training.spring.web.controller.dto.TeacherDto;
import victor.training.spring.web.entity.Teacher;
import victor.training.spring.web.repo.TeacherRepo;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class TeacherService {
  @Autowired
  private TeacherRepo teacherRepo;

  // TODO Cacheable for list-of-all
  public List<TeacherDto> getAllTeachers() {
    return teacherRepo.findAll().stream().map(TeacherDto::new).collect(toList());
  }

  // TODO EvictCache(all)
  // TODO Prove stale cache on multiple instances: start a 2nd instance usign -Dserver.port=8081
  // TODO Redis cache
  // TODO custom aspects vs CacheInterceptor
  public void createTeacher(TeacherDto dto) {
    teacherRepo.save(new Teacher(dto.name));
  }

  public void deleteTeacher(Long id) {
    teacherRepo.deleteById(id);
  }

  @Transactional
  // TODO EvictCache
  public void updateTeacher(long id, String newName) {
    Teacher teacher = teacherRepo.findById(id).orElseThrow();
    teacher.setName(newName);
  }
}
