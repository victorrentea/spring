package victor.training.spring.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.security.config.header.PreAuthHeaderPrincipal;

import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.CompletableFuture.completedFuture;

@RestController
public class RollEndpoint {
  @Autowired
  private MyService myService;

  @PostMapping("api/roll")
  public CompletableFuture<String> roll() {
    return myService.method();
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
