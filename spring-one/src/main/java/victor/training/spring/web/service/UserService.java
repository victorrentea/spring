package victor.training.spring.web.service;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import victor.training.spring.web.controller.dto.UserDto;
import victor.training.spring.web.domain.User;
import victor.training.spring.web.repo.UserRepo;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@interface Counted {}

@Slf4j
@Service
@Transactional
public class UserService  {
    private final UserRepo userRepo;
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }


    // Use cases:
//    @Cacheable("country-names")
//    public Map<Locale, Map<String, String>> method() {
//
//    }

    @Cacheable("user-count")
    @Counted
    public long countUsers() {
        new RuntimeException().printStackTrace();
        return userRepo.count();
    }

    // TODO 1 Cacheable
    // TODO 2 EvictCache
    // TODO 3 Prove: Cache inconsistencies on multiple instances: start a 2nd instance usign -Dserver.port=8081
//@Counted
    // TODO 4 Redis cache
    @CacheEvict("user-count")
    public void createUser() {
        userRepo.save(new User("John-" + System.currentTimeMillis()));
    }

    // TODO 5 key-based cache entries
    @Cacheable("user-data")
    public UserDto getUser(long id) {
        return new UserDto(userRepo.findById(id).get());
    }

//    @CacheEvict(cacheNames = "user-data", key = "#id")
    @CachePut(cacheNames = "user-data", key = "#id")
    public UserDto updateUser(long id, String newName) {
        // TODO 6 update profile too -> pass Dto
        User user = userRepo.findById(id).get();
        user.setName(newName);
        return new UserDto(user);
    }
}

@Component
@Aspect
class CountingAspect {
    int totalCount;
//    @Around("execution(* victor.training.spring.web..*DAO.*(..))")
//    @Around("@within(victor.training.spring.web.service.Counted)")
    @Around("@annotation(victor.training.spring.web.service.Counted)")
    public Object countCall(ProceedingJoinPoint pjp) throws Throwable {
        Object result = pjp.proceed();
        totalCount++; // NO! be thread safe
        //database call / files? / network
        System.out.println("Count:  " + totalCount);
        return result;
    }

}