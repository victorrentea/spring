package victor.training.spring.web.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import victor.training.spring.web.domain.User;
import victor.training.spring.web.repo.UserRepo;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService  {
    private final UserRepo userRepo;


    @Cacheable("user-count")
    public long countUsers() {
        return userRepo.count();
   }
//    @CacheEvict("user-data")
    @CachePut(cacheNames = "user-data", key = "#result.id")
    public User createUser(String name) {
        return userRepo.save(new User(name, null, Collections.emptyList()));
    }
//    @CachePut(cacheNames = "user-data", key = "#result.id")
//    public User updateUser(String name) {
//    }
    @Cacheable(cacheNames = {"user-data"}, key = "#id"/*, condition = "#result.status == 200"*/)
    public User getUser(Long id, String paramNefolosit) {
        return userRepo.findById(id).get();
    }

    // TODO 1 Avoid extra query - get from cache
    // TODO 2 Avoid inconsistencies - evict cache
    // Scenario: get(=2), create, get(=2) STALE!!!
    // TODO 3 Avoid inconsistencies on multiple machines - use Redis as centralized cache
    // Scenario: N1.get(=2), N2.get(=2), N1.create, N1.get(=3), N2.get(=2) STALE!!!
    // TODO 4 At restart, clean the Redis cache (with CLR)
    @CacheEvict("user-count")
//    @CacheEvict(value = "user-count") // identic
//    @CacheEvict(cacheNames = "user-count") // identic
    public void createUser() {
        userRepo.save(new User());
    }

}


