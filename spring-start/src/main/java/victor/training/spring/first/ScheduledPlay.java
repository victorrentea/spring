package victor.training.spring.first;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledPlay {
  private static final Logger log = LoggerFactory.getLogger(ScheduledPlay.class);
  @Scheduled(fixedRate = 1000) // dangerous to use in a mutli-instance environment
  // intances might compete against eachother
  // FIX: use a DB lock
  public void myMethod() throws InterruptedException {
    // example:
    // - DB operation (Cleanup, delete, OUTBOX_TABLE) ❌
    // - deleting temp files on disk ✅ < workout logic per instance : files/in-memory cache...
    log.info("ScheduledSTART");
    Thread.sleep(2000); // not re-entrant by default
    log.info("ScheduledEND");
  }
}
