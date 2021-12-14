package victor.training.spring.web.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import victor.training.spring.web.controller.dto.UserDto;
import victor.training.spring.web.domain.User;
import victor.training.spring.web.repo.UserRepo;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService  {
    private final UserRepo userRepo;

    // special ca NU ARE PARAMETRII
    // eg reale: @Cacheable("countries") List<Country> getAllCountries();
    @Cacheable("user-count")
    public long countUsers() {
        return userRepo.count();
    }

    // TODO 1 Cacheable
    // TODO 2 EvictCache
    // TODO 3 Prove: Cache inconsistencies on multiple instances: start a 2nd instance usign -Dserver.port=8081
    // TODO 4 Redis cache
//    @Scheduled(fixedRate = 5*60*1000)
//    @Scheduled(cron = "* 2 * * 0 *")
    @CacheEvict(value = "user-count", allEntries = true)
    public void createUser(String username) {
        if (username.length() < 3) {
            throw new IllegalArgumentException("Mesaj frumos");
        }
        userRepo.save(new User(username));
    }

    // TODO 5 key-based cache entries
    @Cacheable("user-data")
    public UserDto getUser(long id) {
        return new UserDto(userRepo.findById(id).get());
    }

    @Autowired
    private CacheManager  cacheManager;

//    @CacheEvict(value = "user-data", key = "#id")
    @CachePut(value = "user-data", key = "#id") // in general de evitat, prefera Evict,
    public UserDto updateUser(long id, String newName) {
        // alternative programatic cache
//        ValueWrapper cacheEntry = cacheManager.getCache("user-data").get(id);

        // TODO 6 update profile too -> pass Dto
        User user = userRepo.findById(id).get();
        user.setName(newName);
        return new UserDto(user);
    }
}

