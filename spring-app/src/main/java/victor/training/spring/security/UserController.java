package victor.training.spring.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
//import victor.training.spring.security.config.keycloak.KeyCloakUtils;
import victor.training.spring.security.config.keycloak.TokenUtils;
import victor.training.spring.web.controller.dto.CurrentUserDto;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController {
  static private String currentUsername;
  private final UserService selfInject;

  @GetMapping("api/user/current")
  public CurrentUserDto getCurrentUser() {
    log.info("Return current user");
    CurrentUserDto dto = new CurrentUserDto();
    dto.username = selfInject.getUsername().join();
    Object wtf = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    // in functie de cum ai autentificat requestul, poti avea diverse instante aici
//    User daca ai venit din Basic/USername passowrd
    // KeyCloakPrincipal daca requestul a venit cu JWT in header
    dto.authorities = List.of(); // TODO
    return dto;
  }
}
@Slf4j
@Service
class UserService {
  @Async
  protected CompletableFuture<String> getUsername() {
    log.info("run");
    return CompletableFuture.completedFuture(SecurityContextHolder.getContext().getAuthentication().getName());
  }
}
