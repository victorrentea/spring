package victor.training.spring.web;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.security.config.header.PreAuthHeaderPrincipal;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static java.util.concurrent.CompletableFuture.completedFuture;

@RestController
public class RollEndpoint {
  @Autowired
  private MyService myService;

  @PostMapping("api/roll")
  public CompletableFuture<String> roll() {
    return myService.method();
  }

//  @Scheduled(fixedDelay = 1000)

//  @Scheduled(cron = "/5 * * * * *") //

  // a spring @Scheduled that runs every 10 seconds using cron



//@KafkaListener(topics = "myTopic")
  @Scheduled(cron = "*/5 * * * * *")
  public void imagineAKafkaListener() throws ExecutionException, InterruptedException {
    System.out.println("Scheduled ticks");
    SecurityContext emptyContext = SecurityContextHolder.createEmptyContext();
    PreAuthHeaderPrincipal principal = new PreAuthHeaderPrincipal("jsnow", "12345", List.of());
    emptyContext.setAuthentication(new PreAuthenticatedAuthenticationToken(principal, null));
    SecurityContextHolder.setContext(emptyContext);
    String s = myService.method().get();
    System.out.println(s);
  }
}

@Slf4j
@RequiredArgsConstructor
@Service
class MyService {


  @Async
  public CompletableFuture<String> method() {
    SecurityContext context = SecurityContextHolder.getContext();
    PreAuthHeaderPrincipal principal = (PreAuthHeaderPrincipal) context.getAuthentication().getPrincipal();
    return completedFuture(principal.getSessionId());
  }
}
