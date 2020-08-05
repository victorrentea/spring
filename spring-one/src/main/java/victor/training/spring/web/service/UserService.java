package victor.training.spring.web.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import victor.training.spring.web.repo.UserRepo;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService  {
    private final UserRepo userRepo;

//    static Map<String, String> cachePtM = new HashMap<>();
//    static Map<List<Object>, String> cachePtM2 = new HashMap<>();
//    static Map<Void, Long> usersCount = new HashMap<>();
//
//    @Cacheable("m")
//    public String m(String s) {
//
//    }
//    @Cacheable("m2")
//    public String m2(String s, String p, Integer x) {
//        m(s);
//    }
//    @CacheEvict("m2")
//    public String evictM(String s, String p, Integer x) {
//
//    }

    @Cacheable("usersCount")
    public long countUsers() {
        return userRepo.count();
    }

    // TODO 1 Avoid extra query - get from cache
    // TODO 2 Avoid inconsistencies - evict cache
    // Scenario: get(=2), create, get(=2) STALE!!!
    // TODO 3 Avoid inconsistencies on multiple machines - use Redis as centralized cache
    // Scenario: N1.get(=2), N2.get(=2), N1.create, N1.get(=3), N2.get(=2) STALE!!!
    // TODO 4 At restart, clean the Redis cache (with CLR)
    @CacheEvict(cacheNames = "usersCount", allEntries = true)


    public void createUser() {
        userRepo.save(new victor.training.spring.web.domain.User());
    }

    @Async
    public CompletableFuture<String> getCurrentUsername() {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = user.getUsername();
        return CompletableFuture.completedFuture(username);
    }
}

