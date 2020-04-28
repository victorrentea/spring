package victor.training.spring.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import victor.training.spring.web.domain.User;
import victor.training.spring.web.repo.UserRepo;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;

    @Cacheable("user-count")
    public long countUsers() {
        return userRepo.count();
        //count -> 2 ; create ; count ->
    }
    @CacheEvict("user-count")
    public void createUser() {
        userRepo.save(new User());
    }
}

