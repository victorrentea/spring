package victor.training.spring.web.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
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
@RequiredArgsConstructor
public /*final*/ class UserService  {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CacheManager cacheManager;

    @Cacheable("users-count")
//    @PreAuthorized()
//    @Transactional(propagation = Propagation.REQUIRES_NEW)
//    @Aync
//    @Retryable(3)
    public   /*final*/  long countUsers() {
        log.debug("Calling service method");
        return userRepo.count();
    }

    public long oups() {
        // calling an annotated method from the same class NEVER works in spring !
        // other examples
//        java.lang.reflect.Proxy.
        return countUsers();
    }

// A proxy is an object besides the behavior of a bean
// adds additional behavior to your methods

    // TODO 1 Cacheable
    // TODO 2 EvictCache
    // TODO 3 Prove: Cache inconsistencies on multiple instances: start a 2nd instance usign -Dserver.port=8081
    // TODO 4 Redis cache
    @CacheEvict(value = "users-count", allEntries = true)
    public void createUser(String username) {
//        cacheManager.getCache("users-count").evict(username);
        userRepo.save(new User(username));
    }


    // TODO 5 key-based cache entries
    @Cacheable("user-data")
    public UserDto getUser(long id) {
        return new UserDto(userRepo.findById(id).get());
    }

    @CacheEvict(value = "user-data", key = "#userId")
    // remove the entry from the cache named "user-data" that is under the key="<the value of the id param>"
    public void updateUser(long userId, String newName) {
//        cacheManager.getCache("user-data").evict(id);

        // TODO 6 update profile too -> pass Dto
        User user = userRepo.findById(userId).get();
        user.setName(newName);
    }
}

