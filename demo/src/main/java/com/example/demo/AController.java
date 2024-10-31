package com.example.demo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AController {
  private final AService aService;
  private final Props props;
  private final UserRepository repo;
  private final Features features;

  @GetMapping
  public String hi() {
    boolean ePananCraciun = LocalDate.now().isBefore(props.b());
    return aService.f() + ePananCraciun + " " + features.flags();
  }
  @GetMapping("/users")
  @Operation(summary = "Get all users")
  public List<User> users() {
    return repo.findAll();
  }

  record UserDto(
      @Schema(description = "The user's full name")
      String name,
      String email) {
  }
  @PostMapping
  public UserDto create(@RequestBody UserDto userDto) {
    return userDto;
  }



  @GetMapping("/modify") // trebuia PUT
  @Transactional // #1 autoflush dirty
//  @Async // exec metoda pe un nou thread
  public void modify() {
    bizLogic();
  }

  private void bizLogic() {
    log.info("Unde sunt?!!");
    User user = repo.findById(1).get();
    user.setName("modificat");
//    repo.save(user); // #2 manual save
    throw new IllegalArgumentException("Oups");
  }

  // cron exp = regular expression pentru timp
  // fiecare duminica noaptea la 03:00
  // @Scheduled(cron = "0 0 3 * * SUN")
  @Scheduled(fixedRateString = "${polling.millis}")
  // de evitat cand deployezi pe 2+ masini si executi cu DB unica.
  // folseste SchedLock
  public void ping() {
    log.info("pong");
  }

}
// controller sa cheme service
// copy-paste la LoggerAspect.java
// adnotati cu @Log metoda din service chemata din controller
// observati in consola logul

// vreau sa poti pune adnotarea si pe clasa, nu doar pe metoda
// daca nu merge, intreaba pe chatGPT chat.openai.com
// "cum pot sa fac un aspect sa intercepteze toate
// metodele dintr-o clasa care are o anumita adnotare
// sau adnotarea e pe metoda? in spring framework
//
// foloseste 1 singur @Around