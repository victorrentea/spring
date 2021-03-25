package victor.training.spring.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import victor.training.spring.ThreadUtils;

import java.util.concurrent.ThreadPoolExecutor;

@SpringBootApplication
@Slf4j
@EnableScheduling
@EnableAsync
public class ScheduledApp  {
    public static void main(String[] args) {
        SpringApplication.run(ScheduledApp.class);
    }

    // TODO 1 Should run every 5 seconds / configurable / cron "*/5 * * * * *"
    // TODO 3 Play with delays. cron vs fixedRate? Overlapping executions?
    // TODO 4 Should run on a separate 1-thread pool

    @Autowired
    OtherClass otherClass;
    @Scheduled(fixedRateString = "${poller.rate}")
//    @Scheduled(cron = "*/5 * * * * *")
    public void lookIntoFolder() {
        otherClass.lookIntoFolder();
    }

    // TODO 2 define another task at each second. This should not block.
    // TODO explore application.properties (thread #)
    @Scheduled(fixedRate = 1000)
    public void pollFast() {
        log.debug("FAST each second");
    }
}

@Component
class OtherClass {
    private static final Logger log = LoggerFactory.getLogger(OtherClass.class);

    @Async
    public void lookIntoFolder() {
        log.debug("Looking into folder");
        ThreadUtils.sleep(7000);
        log.debug("DONE");
    }
}