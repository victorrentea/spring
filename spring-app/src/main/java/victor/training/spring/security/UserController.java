package victor.training.spring.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
//import victor.training.spring.security.config.keycloak.KeyCloakUtils;
import victor.training.spring.security.config.keycloak.TokenUtils;
import victor.training.spring.web.controller.dto.CurrentUserDto;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController {
  @GetMapping("api/user/current")
  public CurrentUserDto getCurrentUser() {
    log.info("Return current user");
    CurrentUserDto dto = new CurrentUserDto();
    dto.username = "<todo-username>"; // TODO
    dto.authorities = List.of(); // TODO
    return dto;
  }
}
