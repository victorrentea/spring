package victor.training.spring.security;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.web.controller.dto.CurrentUserDto;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4jW
@RequiredArgsConstructor
@RestController
public class UserController {
  private final Different different;

  @Scheduled(fixedRateString = "${polling.interval.m:1000}") //starts every 1s
//  @Scheduled(fixedDelay = 1000) // starts 1s after the previous execution finishes
//  @Scheduled(cron = "0 */5 * * * *")
  // ⚠️ We do realize this with fire up on all the instances of your application!
  // to avoid race, convert this into a GET endpoint and callit via k8s at fixed time
  public void method() {
  }


  @GetMapping("api/user/current")
  public CurrentUserDto getCurrentUser() throws ExecutionException, InterruptedException {
    log.info("Return current user");
    CurrentUserDto dto = new CurrentUserDto();
    dto.username =  different.someUtil(); // for example to write in CREATED_BY column in a DB
//    dto.username = CompletableFuture.supplyAsync(() -> different.someUtil()).get(); // for example to write in CREATED_BY column in a DB
    dto.authorities = List.of(); // TODO
    return dto;
  }
}
@Service
@Slf4j
class Different {
  String someUtil() {
    //I might have 50 users running simultaneously different requests in an application.
    // How the heck can I get the user name with a static method?
    // The answer is ThreadLocal. Spring Security stores the user information in a magical Thread Local variable
    // which moves invisibly wherever your thread goes
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    log.info("in which thread am I? " + username);

    return username;
    // other pieces of data that move seamlessly with the thread are
    // - SecurityContext
    // - TraceID (for distributed tracing)
    // - MDC (for logging)
    // - TransactionContext (for DB transactions)

    // webflux can't use threadlocal, but it has a similar concept of "Reactor Context"
    // that is propagated across reactive chains
  }
  // you can loose metadata:
  // - Spring WEb running on a different thread not managed by spring : eg CompletableFuture.runAsync(() -> different.someUtil())
  // - Spring WebFlux backgrounTaskMono.flatMap(e->service.securedMethod(e)).subscribe();

}
