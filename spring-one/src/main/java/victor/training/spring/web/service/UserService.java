package victor.training.spring.web.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import victor.training.spring.web.domain.User;
import victor.training.spring.web.repo.UserRepo;
import victor.training.spring.web.security.SecurityUser;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService  {

    @Async
    public CompletableFuture<String> getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityUser myDataFromLoginTime = (SecurityUser) authentication.getPrincipal();
        String currentUsername = authentication.getName();

        System.out.println("LAST_MODIFIED_BY = " +currentUsername);
        return CompletableFuture.completedFuture("Welcome " + myDataFromLoginTime.getName());
    }

    private final UserRepo userRepo;

    @Cacheable("fox")
    public long countUsers() {
        return userRepo.count();
    }

    @CacheEvict("fox")
    public void createUser() {
        userRepo.save(new User());
    }



    //   --> findById(id):IncidentType or findAllAsMap: Map<Long, IncidentType>
    // USE FOR STATIC REFERENCE DATA Country, CITY List
    // almos never changes and when it changes, you can throw away everything

    @Cacheable("all-users")
    public Map<Long, User> findAllUsers() { // one single hit to DB
        List<User> all = userRepo.findAll();
        return all.stream().collect(Collectors.toMap(User::getId, user -> user));
    }
    @CacheEvict("all-users") // one single entry in this cache.
    public void evictStaticCache() {
        // empty method. Please do not delete. "Let the magic happen"
    }


    // you can think of the followingcahce as a
//     HashMap<Long, User> users;

    // all the caches in the system are:
    // Map<String, Map<?,?>>
    private final CacheManager cacheManager;
//        cacheManager.getCache("users").get(id);

//    @Cacheable("users")
//    public User findById(long id, String s) { // Pro: you don't typically use ALL users list;
//    }


    // Map<RequestObj, User>
    @Cacheable(cacheNames = "users", key = "#id")
    public User findById(RequestObj id, Object unused) { // Pro: you don't typically use ALL users list;
        //PRO + in case you want to invalidate, you selectively kill one user
        // expire individually the entries.
        return userRepo.findById(id.getId()).get();
    }

    @Transactional
    @CacheEvict("users")
    public void updateUser(Long id) {
        User user = userRepo.findById(id).get();
        user.setUsername(user.getUsername() + "1");
    }

    // pe doua noduri => tot gresit sa ai cache-uri izolate (de ex in memory)
    // Scenariu:
    // N1: get (=2)
    // N2: get (=2)
    // N1: create
    // N1: get (=3)
    // N2: get (=2) GRESIT
}

