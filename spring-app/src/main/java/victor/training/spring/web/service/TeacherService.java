package victor.training.spring.web.service;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import victor.training.spring.web.controller.dto.TeacherDto;
import victor.training.spring.web.entity.ContractType;
import victor.training.spring.web.entity.Teacher;
import victor.training.spring.web.repo.TeacherRepo;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Logged
public class TeacherService {
    @Autowired
    private TeacherRepo teacherRepo;

    //cache la coada vacii
//    private Map<param, returnedValue> teachers;

    @Logged
    // TODO 1 Cacheable of 'static data'
    @Cacheable("all-teachers")
    public List<TeacherDto> getAllTeachers() {
        System.out.println("Get all");
        return teacherRepo.findAll().stream().map(TeacherDto::new).collect(toList());
    }

    // TODO 2 EvictCache
    // TODO 3 Prove: Cache inconsistencies on multiple instances: start a 2nd instance usign -Dserver.port=8081
    // TODO 4 Redis cache
    @CacheEvict(value = "all-teachers", allEntries = true)
    public void createTeacher(TeacherDto dto) {
        teacherRepo.save(new Teacher(dto.name));
    }


    public List<TeacherDto> getTeacherByContractType(ContractType contractType) {
        return teacherRepo.findByContractType(contractType)
            .stream().map(TeacherDto::new)
            .collect(toList());
    }

    // TODO 5 Keyed cache entries
    @Cacheable("teacher-by-id")
    public TeacherDto getTeacherById(long id) {
        return new TeacherDto(teacherRepo.findById(id).orElseThrow());
    }

    @Transactional
    @CachePut(value = "teacher-by-id",key = "#id")
    @CacheEvict(value = "all-teachers", allEntries = true)
    // TODO 6 what should I do here
    // TODO 7 custom aspects
    public TeacherDto updateTeacher(long id, String newName) {
        Teacher teacher = teacherRepo.findById(id).orElseThrow();
        teacher.setName(newName);
        return new TeacherDto(teacher);
    }
}
@Retention(RetentionPolicy.RUNTIME)
@interface Logged {

}

@Component
@Aspect
@Slf4j
class LoggingAspect {
//    @Around("execution(* victor..repo..*.*(..))")// evitati package/class name pattersn
    @Order(10)
    @Around("@within(victor.training.spring.web.service.Logged) ") // @annotation daca adnotarea apare pe metoda
    public Object logBefore(ProceedingJoinPoint pjp) throws Throwable {
        log.info("Am interceptat metoda " + pjp.getSignature().getName() + " cu arg " + Arrays.toString(pjp.getArgs()));
        Object result = pjp.proceed();
        return result;
    }

}
