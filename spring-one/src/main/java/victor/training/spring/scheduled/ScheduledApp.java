package victor.training.spring.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import victor.training.spring.ThreadUtils;

@EnableScheduling
@SpringBootApplication
@Slf4j
public class ScheduledApp  {
    public static void main(String[] args) {
        SpringApplication.run(ScheduledApp.class);
    }

    // TODO 1 Should run every 5 seconds / configurable / cron "*/5 * * * * *"
    // TODO 3 Play with delays. cron vs fixedRate? Overlapping executions?
    // TODO 4 Should run on a separate 1-thread pool
//    @Scheduled(fixedRate = 5000)
//    @Scheduled(fixedRateString = "${from.properties.file}")
    @Scheduled(cron = "${cleanup.cron}")
    public void lookIntoFolder() {
        log.debug("Looking into folder");
        ThreadUtils.sleep(7000);
        log.debug("DONE");
    }

    // it might be smarter instead of @Scheduled inside your app, to expose aaREST endpoint (accessible for localhost only)
    // and hit it from a crontab * * * * *  curl localhost:8080/start-job1
    // you can then ssh and curl to start it manually.


    // TODO 2 define another task at each second. This should not block.
    // TODO explore application.properties (thread #)
    public void pollFast() {
        log.debug("FAST each second");
    }
}
