package victor.training.spring.web.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import victor.training.spring.web.domain.User;
import victor.training.spring.web.repo.UserRepo;

import javax.annotation.PostConstruct;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService  {
    private final UserRepo userRepo;

    @Cacheable("user-count")
    public long countUsers() {
        new RuntimeException().printStackTrace();
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
        userRepo.save(new User());
    }

}

