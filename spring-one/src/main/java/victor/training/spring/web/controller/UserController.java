package victor.training.spring.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.controller.dto.UserDto;
import victor.training.spring.web.repo.UserRepo;
import victor.training.spring.web.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService service;
//    = new UserService() {
//        @Override
//        public long countUsers() {//
//            return super.countUsers();
//            //
//        }
//    };
private final UserRepo userRepo;

    @GetMapping("users/count")
    public long count() {
        return service.countUsers(); //works
//        return this.countUsers(); // local metod calls are never proxied
        // no method-level anntation works
    }
    @Cacheable("user-count")
    public /*final*/ long countUsers() {
        return userRepo.count();
    }
    
    @GetMapping("users/create")
    public void create() {
        service.createUser();
    }

    

    @GetMapping("users/{id}")
    public UserDto get(@PathVariable long id) {
        return service.getUser(id);
    }
    @PutMapping("users/{id}")
    public void update(@PathVariable long id, @RequestBody UserDto dto) {
        service.updateUser(id, dto.name);
    }

}
