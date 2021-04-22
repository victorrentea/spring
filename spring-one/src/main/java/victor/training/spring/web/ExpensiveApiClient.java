package victor.training.spring.web;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import victor.training.spring.ThreadUtils;

@Slf4j
@RequiredArgsConstructor
@Service
public class ExpensiveApiClient {
   @Timed("external call")
   public String blockingCall() {
      log.info("Calling sync...");
      ThreadUtils.sleep(1000);
      log.info("DONE");
      return "data " + System.currentTimeMillis();
   }
}
