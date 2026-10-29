package victor.training.spring.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.web.controller.dto.CurrentUserDto;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController {

  @GetMapping("api/user/current")
  public CurrentUserDto getCurrentUser() throws ExecutionException, InterruptedException {
    log.info("Return current user");
    CurrentUserDto dto = new CurrentUserDto();
    dto.username = alta();
    dto.authorities = List.of(); // TODO
    return dto;
  }

  private String alta() {
    // Informația despre userul curent vine ștampilată pe fundul thread-ului.
    // Oriunde ajunge thread-ul, dacă a venit din endpoint HTTP, poți să scrii linia asta,
    // să scoți numele userului logat.
    return SecurityContextHolder.getContext().getAuthentication().getName();
  }
}
