package victor.training.spring.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import victor.training.spring.ThreadUtils;

@SpringBootApplication
@Slf4j
@EnableScheduling
public class ScheduledApp  {
    public static void main(String[] args) {
        SpringApplication.run(ScheduledApp.class);
    }

    // TODO 1 Should run every 5 seconds / configurable / cron "*/5 * * * * *"
    // TODO 3 Play with delays. cron vs fixedRate? Overlapping executions?
    // TODO 4 Should run on a separate 1-thread pool
//    @Scheduled(fixedRateString = "${folder.poll}")
    @Scheduled(cron = "*/5 * * * * *")
    public void lookIntoFolder() {
        log.debug("Looking into folder");
        ThreadUtils.sleep(7000);
        log.debug("DONE");
        // LAST_MODIFIED_BY=job1
    }

    // TODO 2 define another task at each second. This should not block.
    // TODO explore application.properties (thread #)
    @Scheduled(fixedRate = 1000)
    public void pollFast() {
        log.debug("FAST each second");
    }
}
