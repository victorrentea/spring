package victor.training.spring.web.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import victor.training.spring.web.controller.dto.UserDto;
import victor.training.spring.web.domain.User;
import victor.training.spring.web.repo.UserRepo;

import java.io.File;
import java.util.concurrent.CompletableFuture;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
@Logged
public class UserService  {
    private final UserRepo userRepo;

    @Cacheable("user-count")
    public long countUsers() {
        return userRepo.count();
    }
//    @Cacheable("countries")
//    public Map<String, Country> getAllCountries() {
//        return userRepo.count();
//    }
//    @Cacheable("vegetables")
//    public Map<String, Country> getAllVegetableTypes() {
//        return userRepo.count();
//    }

//    @Autowired
//    private UserService iHopeMyBossDoesntSeeThis;

    @Autowired
    private ApplicationContext context;


    public long suprise() {
//        UserService myselfProxied = (UserService) AopContext.currentProxy();
        UserService iHopeMyBossDoesntSeeThis = context.getBean(UserService.class);
        return iHopeMyBossDoesntSeeThis.countUsers();
    }

    // TODO 1 Cacheable
    // TODO 2 EvictCache
    // TODO 3 Prove: Cache inconsistencies on multiple instances: start a 2nd instance usign -Dserver.port=8081
    // TODO 4 Redis cache
    @CacheEvict("user-count")
    public void createUser() {
        userRepo.save(new User("John-" + System.currentTimeMillis()));
    }

    // TODO 5 key-based cache entries
    @Cacheable("user-data")
    public UserDto getUser(long id) {
        new RuntimeException("Intentional").printStackTrace();
        return new UserDto(userRepo.findById(id).get());
    }

    @CacheEvict(value = "user-data",allEntries = true)
    public void onBulkUpdateUsers() {
    }

    @Autowired
    CacheManager cacheManager;

    @CachePut(value = "user-data",key = "#id")
    public UserDto updateUser(long id, String newName) {
        // TODO 6 update profile too -> pass Dto

//        Cache cache = cacheManager.getCache("user-data");
//        List<Long> updateIds;
//        for (Long idd : updateIds) {
////            (EHChacheCache)cache.getNativeCache().getentry.setTTl
//            cache.evictIfPresent(idd);
//        }


        User user = userRepo.findById(id).get();
        user.setName(newName);
        return new UserDto(user);
    }

    @Async
    public CompletableFuture<String> getCurrentUsername() {
//       if(true) throw new RuntimeException("e");
        return CompletableFuture.completedFuture(SecurityContextHolder.getContext()
            .getAuthentication().getName());
    }

    @Async
    public void processUploadedFile(File file) {
        throw new RuntimeException("e");
    }
}

