package victor.training.spring.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import victor.training.spring.ThreadUtils;

@SpringBootApplication
@Slf4j
public class ScheduledApp  {
    public static void main(String[] args) {
        SpringApplication.run(ScheduledApp.class);
    }

    // TODO 1 Should run every 5 seconds / configurable / cron "*/5 * * * * *"
    // TODO 3 Play with delays. cron vs fixedRate? Overlapping executions?
    // TODO 4 Should run on a separate 1-thread pool
//    @Scheduled(fixedRateString= "${folder.poller.rate.millis}")
//    @Scheduled(fixedRateString= "${folder.poller.rate.millis}")
    @GetMapping("admin/poll-folder") // mister: cum securizez acest endpoint
    public void lookIntoFolder() {
        log.debug("Looking into folder");
        ThreadUtils.sleep(7000);
        log.debug("DONE");
    }

    // undeva dintr-un linux *sau din vreun k8s service) se cheama acest url o data la ora dorita.
    // pe prod poti lansa manual din ssh curl de pe prod

    // TODO 2 define another task at each second. This should not block.
    // TODO explore application.properties (thread #)
    public void pollFast() {
        log.debug("FAST each second");
    }
}
