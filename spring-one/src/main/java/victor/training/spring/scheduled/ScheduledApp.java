package victor.training.spring.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import victor.training.spring.ThreadUtils;

import java.util.concurrent.ThreadPoolExecutor;

@SpringBootApplication
@Slf4j
@EnableScheduling
public class ScheduledApp  {
    public static void main(String[] args) {
        SpringApplication.run(ScheduledApp.class);
    }

    // TODO 1 Should run every 5 seconds / configurable / cron "*/5 * * * * *" - diff vs rate
    // TODO 3 Should run on a separate 1-thread pool
//    @Scheduled(fixedRate = 5000)
//    @Scheduled(fixedRateString = "${folder.poller.rate.millis}")
    @Scheduled(cron = "${folder.poller.cron}")
    public void lookIntoFolder() {
        log.debug("Looking into folder");
        ThreadUtils.sleep(4000);
        log.debug("DONE");
    }

    /// over the hills and far away

    // TODO 2 define another task at each second. This should not block.
    // TODO explore application.properties
    @Scheduled(fixedRate = 1000)
    public void pollFast() {
        log.debug("FAST each second");
    }
}
