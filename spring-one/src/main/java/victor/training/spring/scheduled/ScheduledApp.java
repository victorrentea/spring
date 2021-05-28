package victor.training.spring.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import victor.training.spring.ThreadUtils;

@SpringBootApplication
@EnableScheduling
@Slf4j
public class ScheduledApp  {
    public static void main(String[] args) {
        SpringApplication.run(ScheduledApp.class);
    }

    // TODO 1 Should run every 5 seconds / configurable / cron "*/5 * * * * *"
    // TODO 3 Play with delays. cron vs fixedRate? Overlapping executions?
    // TODO 4 Should run on a separate 1-thread pool
//    @Scheduled(fixedRateString = "${folder.polling.rate.millis}")
    @Scheduled(cron = "${folder.polling.cron}")
    public void lookIntoFolder() {
        log.debug("Looking into folder");
        ThreadUtils.sleep(700);
        log.debug("DONE");
    }

    // TODO 2 define another task at each second. This should not block.
    // TODO explore application.properties (thread #)
    public void pollFast() {
        log.debug("FAST each second");
    }
}
