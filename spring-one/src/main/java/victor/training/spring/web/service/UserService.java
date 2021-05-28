package victor.training.spring.web.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import victor.training.spring.web.domain.User;
import victor.training.spring.web.repo.UserRepo;
import victor.training.spring.web.security.SecurityUser;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService  {
    private final UserRepo userRepo;


    @Cacheable("user-count")
    public long countUsers() { // HOT flow
        return userRepo.count();
    }

    // TODO 1 Cacheable
    // TODO 2 EvictCache
    // TODO 3 Prove: Cache inconsistencies on multiple instances: start a 2nd instance usign -Dserver.port=8081
    // TODO 4 Redis cache
    @CacheEvict("user-count")
    public void createUser() { // map.remove
        userRepo.save(new User("John-" + System.currentTimeMillis()));
    }

    // TODO 5 key-based cache entries

    @Cacheable("user-data")
    public User getUser(long id) {
        return userRepo.findById(id).get();
    }

//    @CacheEvict(value = "user-data", key = "#id")
    @CachePut(cacheNames = "user-data", key = "#id")
    public User updateUser(long id, String newName) {
        // TODO 6 update profile too -> pass Dto
        User user = userRepo.findById(id).get();
        user.setName(newName);
        return user;
    }
    @Async
    public CompletableFuture<String> methodaAsync() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SecurityUser deLaLoginPastratPeSesiune = (SecurityUser) principal;

        System.out.println("Teacherii mei: " + deLaLoginPastratPeSesiune.getManagedTeacherIds());

        return CompletableFuture.completedFuture(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}

