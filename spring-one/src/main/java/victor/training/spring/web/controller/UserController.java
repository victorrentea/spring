package victor.training.spring.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.web.domain.User;
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
    public Long create(@RequestParam(defaultValue = "John") String name) {
        return service.createUser(name).getId();
    }
    @GetMapping("users/{id}")
    public User get(@PathVariable long id) {
        return service.getUser(id, "");
    }
}
