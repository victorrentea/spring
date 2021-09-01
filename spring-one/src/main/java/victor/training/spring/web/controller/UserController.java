package victor.training.spring.web.controller;

import org.apache.commons.lang3.RandomStringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.web.controller.dto.UserDto;
import victor.training.spring.web.domain.UserRole;
import victor.training.spring.web.service.UserService;

import java.util.Arrays;

//class UserServiceHacked extends UserService {
//    @Override
//    public UserDto getUser(long id) {
//        return super.getUser(id);
//    }
//}

@RestController
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        System.out.println("I am given by Spring the following UserService class:" + service.getClass());
//        ConcurrentHashMap
//        new UserSer
        this.service = service;
    }

    @GetMapping("users/count")
    public long count() {
//        return service.countUsers();
        return service.suprise();
    }
    @GetMapping("users/create")
    public void create() {
        service.createUser();
    }

    @GetMapping("users/{id}")
    public UserDto get(@PathVariable long id) {
        return service.getUser(id);
    }
    // correct REST way
//    @PutMapping("users/{id}")
//    public void update(@PathVariable long id, @RequestBody UserDto dto) {
//        service.updateUser(id, dto.name);
//    }

    // hacked for convenience (called from browser)
    @GetMapping("users/{id}/update")
    public void update(@PathVariable long id) {
        String newName = RandomStringUtils.randomAlphabetic(10);
        UserDto dto = new UserDto();
        dto.id = id;
        dto.name = newName;
        dto.profile = UserRole.ADMIN;
        service.updateUser(id, dto.name);
    }
}


@Aspect
@Component
class LoggingAspect {
//    @Around("execution(* get*(..))")
//    @Around("execution(* victor.training..*.*(..))")
//    @Around("@annotation(victor.training.spring.web.service.Logged)") // method-level annotations
    @Around("@annotation(victor.training.spring.web.service.Logged) " +
            "|| @within(victor.training.spring.web.service.Logged)") // method or class-level annotations
    public Object interceptAndLog(ProceedingJoinPoint pjp) throws Throwable {

        // datasource.getConnection.update("SET NLS_LANG="...)

        System.out.println("Calling " + pjp.getSignature().getName() +
                           " with arg " + Arrays.toString(pjp.getArgs()));
        Object result = pjp.proceed();
        System.out.println("returning " + result);
        return result;
    }
}