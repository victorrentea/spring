package victor.training.spring.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.*;
import victor.training.spring.web.controller.dto.UserDto;
import victor.training.spring.web.domain.UserRole;
import victor.training.spring.web.service.UserService;

@Slf4j
@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping("count")
    public long count() {
        log.debug("My call goes to a proxy" + service.getClass());
        return service.countUsers();
    }


    @GetMapping("create") // NOT FOLLOWING SEMANTICS OF HTTP VERBS: create should be a POST
    public void create() {
        service.createUser("John-" + System.currentTimeMillis());
    }













    @GetMapping("{id}")
    public UserDto get(@PathVariable long id) {
        return service.getUser(id);
    }
    // correct REST way
//    @PutMapping("{id}")
//    public void update(@PathVariable long id, @RequestBody UserDto dto) {
//        service.updateUser(id, dto.name);
//    }

    // hacked for convenience (called from browser)
    @GetMapping("{id}/update")
    public void update(@PathVariable long id) {
        String newName = RandomStringUtils.randomAlphabetic(10);
        UserDto dto = new UserDto();
        dto.id = id;
        dto.name = newName;
        dto.profile = UserRole.ADMIN;
        service.updateUser(id, dto.name);
    }

}
