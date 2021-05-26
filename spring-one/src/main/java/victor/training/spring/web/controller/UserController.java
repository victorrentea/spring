package victor.training.spring.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.controller.dto.UserDto;
import victor.training.spring.web.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping("users/count")
    public long count() {
        return service.countUsers();
    }
    @GetMapping("users/create")
    public void create() {
        service.createUser();
    }

    @GetMapping("users/kill-cache")
    @CacheEvict("user-count")
    public void killCache() { // curl localhost:8080/users/kill-cache la finalul scriptului care baga inserturile
    }

    @GetMapping("users/{id}")
    public UserDto get(@PathVariable long id) {
        return new UserDto(service.getUser(id));
    }
    @PutMapping("users/{id}")
    public void update(@PathVariable long id, @RequestBody UserDto dto) {
        service.updateUser(id, dto.name);
    }

}
