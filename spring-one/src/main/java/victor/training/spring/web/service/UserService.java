package victor.training.spring.web.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import victor.training.spring.web.controller.UserController;
import victor.training.spring.web.domain.User;
import victor.training.spring.web.repo.UserRepo;

import java.util.Date;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;


    // Tipul 1 de cache: date putine, de obicei statice;
    // eg: country list, site locations, users, currencies, tipuri de permisiuni (featuri disponibile pentru rolul X)
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

    // tipul 2 de cache: cel care pentru param arbitrari stai mult per request.
    // eg: cotatii de costuri la flights pe API externe, date din sisteme terte
    @CacheEvict("user-count")
    public void createUser() {
        userRepo.save(new User());
    }


    // Map<Long, User> user-data;  .get()
    @Cacheable("user-data")
    public User get(long id) {
        return userRepo.findById(id).get();
    }

    // .removeKey
    @CacheEvict("user-data")
    @Transactional
    public void update(long id) {
        User user = userRepo.findById(id).get();
        user.setUsername(new Date().toString());
    }


    // cand pila de la LDAP iti spune ca s-a modificat ceva, orice,
    // iti chemi acest endpoint
    @CacheEvict(cacheNames = "user-data", allEntries = true)
    public void clearAllUserCache() {
        // EMPTY METHOD. DO NOT DELETE. LET THE MAGIC HAPPEN
    }

    @Async
    public CompletableFuture<String> getCurrentUsername() {
        log.debug("Din alt thread: ");
        return CompletableFuture.completedFuture(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}

