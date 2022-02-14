package victor.training.spring.web.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
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

    // --- A: the cache here is key-less: eg: countryList ("reference data")
//    @Cacheable("countries") public List<Country> getAllCountries() {


    @Cacheable("user-count")
    public long countUsers() {
        return userRepo.count();
    }

    // TODO 1 Cacheable
    // TODO 2 EvictCache
    // TODO 3 Prove: Cache inconsistencies on multiple instances: start a 2nd instance usign -Dserver.port=8081
    // TODO 4 Redis cache
    @CacheEvict("user-count")
    public void createUser() {
        userRepo.save(new User("John-" + System.currentTimeMillis()));
    }

    // -- B: the cache below is "keyed":
    // expensive query results, external systems,

    // TODO 5 key-based cache entries
    @Cacheable("user-data")
    public UserDto getUser(long id) {
        return new UserDto(userRepo.findById(id).get());
    }

//    @CachPut // overwrites the entry in the cache instead of evicting it. "avoids a SELECT later"
    @CacheEvict(value = "user-data", key = "#id")
    public void updateUser(long id, String newName) {
        // TODO 6 update profile too -> pass Dto
        User user = userRepo.findById(id).get();
        user.setName(newName);
    }
}

