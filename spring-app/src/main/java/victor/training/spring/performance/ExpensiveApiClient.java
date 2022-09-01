package victor.training.spring.performance;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import victor.training.spring.varie.ThreadUtils;

import java.util.concurrent.CompletableFuture;

@Slf4j
@RequiredArgsConstructor
@Service
@Timed

public class ExpensiveApiClient {
   @Async("customExecutor")
   public CompletableFuture<String> asyncCall() {
      log.info("Calling async...");
      ThreadUtils.sleepq(1000);
      log.info("DONE");
      return CompletableFuture.completedFuture("data");
   }

   public String blockingCall() {
      log.info("Calling sync...");
      ThreadUtils.sleepq(10);
      log.info("DONE");
      return "data " + System.currentTimeMillis();
   }
}
