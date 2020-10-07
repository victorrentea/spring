package victor.training.spring.web.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Cache;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import victor.training.spring.web.domain.User;
import victor.training.spring.web.repo.UserRepo;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService  {
    private final UserRepo userRepo;

//    @Cacheable(cacheNames ="users", key = "#id")
//    public User getById(long id, String useless) {
//        return userRepo.findById(id).get();
//    }

    @Cacheable("user-count")
    public long countUsers() {
        return userRepo.count();
    }

    // TODO 1 Avoid extra query - get from cache
    // TODO 2 Avoid inconsistencies - evict cache
    // Scenario: get(=2), create, get(=2) STALE!!!
    // TODO 3 Avoid inconsistencies on multiple machines - use Redis as centralized cache
    // Scenario: N1.get(=2), N2.get(=2), N1.create, N1.get(=3), N2.get(=2) STALE!!!
    // TODO 4 At restart, clean the Redis cache (with CLR)

    @CacheEvict("user-count")
    public void createUser() {

//        countUsers() // nu e proxiat!
        userRepo.save(new User());
    }

}

