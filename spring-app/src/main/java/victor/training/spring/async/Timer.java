package victor.training.spring.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnProperty(
    name = "scheduler.enabled",
    havingValue = "true",
    matchIfMissing = true)
public class Timer {
  @Async // face taskul re-entrant: te calci pe el singur
  @Scheduled(/*min*/fixedRateString = "${marius.rupe}")
  void poll() throws InterruptedException {
    log.info("START cu-cu verific conex cu DB");
    Thread.sleep(4000);
//    new ScheduledThreadPoolExecutor().
    log.info("END push device status/diagnoza din Elastic in angular");
  }
}
