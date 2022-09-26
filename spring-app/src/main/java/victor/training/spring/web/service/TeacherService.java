package victor.training.spring.web.service;

import com.sun.xml.bind.v2.TODO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    // TODO Cacheable for list-of-all
    @Cacheable("all-teachers") // please imagine this defines a Map<Nothing, List<TeacherDto>> allTeachers;
    public List<TeacherDto> getAllTeachers() {
        return teacherRepo.findAll().stream().map(TeacherDto::new).collect(toList());
    }

//    @Cacheable("products")
//    public String fetchExpensiveDataFrom3rdPartyApi(long productId) {
//
//    }

    @Autowired
    CacheManager cacheManager;

    @CacheEvict(value = "all-teachers",allEntries = true)
    // TODO EvictCache(all)
    // TODO Prove stale cache on multiple instances: start a 2nd instance usign -Dserver.port=8081
    // TODO Redis cache
    // TODO custom aspects vs CacheInterceptor
    public void createTeacher(TeacherDto dto) {
//        cacheManager.getCache("all-teachers").clear(); // programmatic alternative to @CacheEving
        teacherRepo.save(new Teacher(dto.name));
    }

    public void deleteTeacher(Long id) {
        teacherRepo.deleteById(id);
    }

    @CacheEvict("all-teachers")
    public void updateTeacher(long id, String newName) {
        Teacher teacher = teacherRepo.findById(id).orElseThrow();
        teacher.setName(newName);
        teacherRepo.save(teacher);
    }
}
