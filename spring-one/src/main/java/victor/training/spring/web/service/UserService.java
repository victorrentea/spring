package victor.training.spring.web.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import victor.training.spring.web.controller.dto.UserDto;
import victor.training.spring.web.domain.User;
import victor.training.spring.web.repo.UserRepo;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService  {
    private final UserRepo userRepo;


    @Cacheable("users-count") // use-case: sa memorez in Java date care se modifica ff rar in DB
    // eg: lista de countries, de currencyuri, de FEX (pe parcursul unei zile), lista de useri
    public long countUsers() {
        return userRepo.count();
    }

    // TODO 1 Cacheable
    // TODO 2 EvictCache
    // TODO 3 Prove: Cache inconsistencies on multiple instances: start a 2nd instance usign -Dserver.port=8081
    // TODO 4 Redis cache
    @CacheEvict("users-count")
    public void createUser() {
        userRepo.save(new User("John-" + System.currentTimeMillis()));
    }

    // TODO 5 key-based cache entries

    Map<Long, UserDto> userDataCache = new HashMap<>();

    @Cacheable("user-data")// userDataCache.remove(id);
    public UserDto getUser(long id) {
        return new UserDto(userRepo.findById(id).get());
    }

    @CacheEvict(value = "user-data",  key = "#id") // userDataCache.remove(id);
    public void updateUser(long id, String newName) {
        // TODO 6 update profile too -> pass Dto

        User user = userRepo.findById(id).get();
        user.setName(newName);
    }

//    public void curataTotCacheulDeUseri() {
//        // MAGIC! DO NOT TOUCH!
//    }

    @CacheEvict(value = "user-data", allEntries = true)
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Scheduled(fixedRateString = "${cache.autoevict.rate.millis}")
//    @Scheduled(cron = "${cache.autoevict.cron}")
    public void timer() {
        log.info("acum rulez");
//        curataTotCacheulDeUseri();
    }
}

