package victor.training.spring.web.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.config.AopConfigUtils;
import org.springframework.aop.framework.AopConfigException;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import victor.training.spring.web.controller.dto.UserDto;
import victor.training.spring.web.domain.User;
import victor.training.spring.web.repo.UserRepo;

@Slf4j
@Service
@Transactional
public class UserService  {
    private final UserRepo userRepo;


    @Autowired
    private  UserService myselfProxied;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Cacheable("user-count")
    public long countUsers() {
//        UserService o = (UserService) AopContext.currentProxy();
        return countUsers_();
    }
    // these never work if the method is called locall.
//    @Transactional
//    @Async
//    @PreAuthorised("hasRole('ADMIN'")
    public long countUsers_() {
        return userRepo.count();
    }

    // TODO 1 Cacheable
    // TODO 2 EvictCache
    // TODO 3 Prove: Cache inconsistencies on multiple instances: start a 2nd instance usign -Dserver.port=8081
    // TODO 4 Redis cache
    @CacheEvict("user-count")
    public void createUser() {
        userRepo.save(new User("John-" + System.currentTimeMillis()));
    }






    // TODO 5 key-based cache entries

    public UserDto getUser(long id) {
        return new UserDto(userRepo.findById(id).get());
    }

    public void updateUser(long id, String newName) {
        // TODO 6 update profile too -> pass Dto
        User user = userRepo.findById(id).get();
        user.setName(newName);
    }
}

