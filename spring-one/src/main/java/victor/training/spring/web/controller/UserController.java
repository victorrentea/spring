package victor.training.spring.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.web.domain.User;
import victor.training.spring.web.repo.UserRepo;
import victor.training.spring.web.service.RequestObj;
import victor.training.spring.web.service.UserService;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService service;
    private final UserRepo repo;


    @GetMapping("users/count")
    public long count() {
        return service.countUsers();
    }

    @GetMapping("users/create")
    public void create() {
        service.createUser();
    }



    /// ==============

    @GetMapping("users/{id}")
    public User get(@PathVariable long id) {
        return service.findById(new RequestObj(id), LocalDateTime.now());
//        return service.findAllUsers().get(id);
    }

    // ssh> curl localhost:8080/admin/kill-cache
    @GetMapping("/admin/kill-cache")
    public void killCache() {
        service.evictStaticCache();
    }


    //    @GetMapping("users")
//    public List<User> getAll() {
//        return repo.findAll();
//    }
    @GetMapping("users/{id}/update")
    public void update(@PathVariable long id) {
       service.updateUser(id);
    }

}
