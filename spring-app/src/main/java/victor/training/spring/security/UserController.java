package victor.training.spring.security;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
//import victor.training.spring.security.config.keycloak.KeyCloakUtils;
import victor.training.spring.web.controller.dto.CurrentUserDto;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController {
  @GetMapping("api/user/current")
  public CurrentUserDto getCurrentUser() {
    MDC.put("sessionId", new Random().nextInt(1000)+"");
    log.info("Return current user");
    CurrentUserDto dto = new CurrentUserDto();
//    dto.username = authentication.getName(); // ❌
    dto.username = serviceMethod(); // ✅ oriunde

    // cum iei date in plus din token:
//    Object opaquePrinciplaInFctDeCumTeaiLogat = SecurityContextHolder.getContext().getAuthentication().getPrincipal();''
//    KeyCloakPrincipal principal = (KeyCloakPrincipal)opaquePrinciplaInFctDeCumTeaiLogat;
//    principal.getIdToken().getClaim("phoneNumber");
    dto.authorities = List.of(); // TODO
    return dto;
  }

  @SneakyThrows
  private String serviceMethod() {
//    return CompletableFuture.supplyAsync(()->repoMethod(), taskExecutor).get();
    return repo.repoMethod().get();
    //⭐️ MEREU dai param la orice CFuture....Async( un executor luar de la spring, decorat sa propage thread local data:
    // a) SecurityContext
    // b) traceid (opentelementry)
    // c) Logback MDC ~> OTEL baggage
  }
  private final ThreadPoolTaskExecutor taskExecutor;
  private final Repo repo;
}
@Slf4j
@RequiredArgsConstructor
@Repository
class Repo{
  @Async
  public CompletableFuture<String> repoMethod() {
    log.info("Din alt thread");
    return CompletableFuture.completedFuture(SecurityContextHolder.getContext().getAuthentication().getName());
  }
}
