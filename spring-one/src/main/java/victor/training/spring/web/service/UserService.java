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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import victor.training.spring.web.domain.User;
import victor.training.spring.web.repo.UserRepo;

import javax.annotation.PostConstruct;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements CommandLineRunner {

    @Async
    public Future<String> getCurrentUsername() {
        log.debug("pe ce thread sunt?");
        return CompletableFuture.completedFuture(SecurityContextHolder.getContext().getAuthentication().getName());
    }





    private final UserRepo userRepo;

    @Cacheable("user-count")
    public long countUsers() {
        return userRepo.count();
    }

    //count -> 2 ; create ; count -> tot 2 (gresit) -> solutie: adauga cacheEvict
    @CacheEvict("user-count")
    public void createUser() {
        userRepo.save(new User());
    }

    @Override
    @CacheEvict("user-count")
    public void run(String... args) throws Exception {
        System.out.println("MERGE!!! CLR poate fi interceptat/proxiat");
    }

    // pe doua noduri => tot gresit sa ai cache-uri izolate (de ex in memory)
    // Scenariu:
    // N1: get (=2)
    // N2: get (=2)
    // N1: create
    // N1: get (=3)
    // N2: get (=2) GRESIT
}

