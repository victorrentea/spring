package victor.training.spring.web.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import victor.training.spring.web.controller.dto.UserDto;
import victor.training.spring.web.domain.User;
import victor.training.spring.web.repo.UserRepo;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
@Logged
public class UserService  {
    private final UserRepo userRepo;


//    @Cacheable("warehouse")
//    public List<String> getAllWarehouses() {
//        return repo.find();
//    }

    @Cacheable("user-count")
    public long countUsers() {
        return userRepo.count();
    }

//    @CacheEvict("user-count") 1
    public void createManyUser() {
        for (int i = 0; i < 10; i++) {
            createUser(); // local method call don't get proxied.
//            applicationContext.getBean(UserService.class).createUser(); // hard to test
//            userServiceProxied.createUser(); // weird
            // AopContext.currentProxy() // too magic
        }
    }

    @Autowired
    private UserService userServiceProxied;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private CacheManager cacheManager;

//    @CacheEvict("user-count")
    public void createUser() {
        cacheManager.getCache("user-count").clear(); // programatic alternative to annotation.
        userRepo.save(new User("John-" + System.currentTimeMillis()));
    }

    // TODO 5 key-based cache entries
    @Cacheable("user-data")
    public UserDto getUser(long id) {
        return new UserDto(userRepo.findById(id).get());
    }

//    @CacheEvict(value = "user-data", key = "#id")
    @CachePut(value = "user-data", key = "#id")
    @Transactional
    public UserDto updateUser(long id, String newName) {
        User user = userRepo.findById(id).get();
        user.setName(null);
//userRepo.save
        return new UserDto(user);
    }


}

