package victor.training.spring.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.controller.dto.UserDto;
import victor.training.spring.web.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService service;
//        = new UserService() {
//        @Override
//        public long countUsers() {
//            // first check the cache if threr-> return from cache
//            long ret = super.countUsers();
//            // cache put (ret)
//            return ret;
//        }
//    };

    @GetMapping("users/count")
    public long count() {
        System.out.println("What you expect ; " + service.getClass());
        return service.countUsers();
    }
    @GetMapping("users/create")
    public void create() {

//        for (int i = 0; i < 100; i++) {
            service.createUser();

//        }
    }

    /////////////////

    @GetMapping("users/{id}")
    public UserDto get(@PathVariable long id) {
        return service.getUser(id);
    }
    @PutMapping("users/{id}")
    public void update(@PathVariable long id, @RequestBody UserDto dto) {
        service.updateUser(id, dto.name);
    }
    @GetMapping("users/{id}/update")
    public void updateGet(@PathVariable long id) {
        service.updateUser(id, "dto.name " +System.currentTimeMillis());
    }

}
