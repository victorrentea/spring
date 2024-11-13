package victor.training.spring.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
//import victor.training.spring.security.config.keycloak.KeyCloakUtils;
import victor.training.spring.security.config.keycloak.TokenUtils;
import victor.training.spring.web.controller.dto.CurrentUserDto;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static java.util.concurrent.CompletableFuture.completedFuture;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController {
  private final AnotherClass anotherClass;

//  static String currentUsername; // rau ca pot exista mai multe req HTTP in executie

  @GetMapping("api/user/current")
  public CurrentUserDto getCurrentUsername() throws ExecutionException, InterruptedException {
    TokenUtils.printTheTokens();
    MDC.put("correlationId", "zau?"); // = Logback Diagnostic Context pe ThreadLocal
    log.info("Return current user");
    met();
    CurrentUserDto dto = new CurrentUserDto();
    // in Java exista un clasa magica numita ThreadLocal in care pot pune userul de pe acest thread
    Authentication authentication = anotherClass.getUser().get();
    dto.username = authentication.getName();
    dto.authorities = authentication.getAuthorities().stream().map(Object::toString).toList();
    return dto;
  }
  private void met() {
    log.info("Tot in fluxu meu dar 20 de clase mai incolo");
  }
  @Slf4j
  @Service
  public static class AnotherClass {
    @Async
    public CompletableFuture<Authentication> getUser() {
      log.info("pe ce thread sunt maica?");
      return completedFuture(SecurityContextHolder.getContext().getAuthentication());
    }
  }

}
