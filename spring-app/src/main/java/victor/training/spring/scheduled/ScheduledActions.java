package victor.training.spring.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import victor.training.spring.varie.ThreadUtils;

@Slf4j
@Component
public class ScheduledActions {

    // TODO 1 Should run every 5 seconds / configurable / cron "*/5 * * * * *"
    // TODO 3 Play with delays. cron vs fixedRate? Overlapping executions?
    // TODO 4 Should run on a separate 1-thread pool

//    @Scheduled(fixedRateString = "${rate.millis}")
    @Scheduled(fixedRateString = "${rate.millis}")
    public void lookIntoFolder() {
        log.debug("Looking into folder");
        ThreadUtils.sleepq(7000);
        log.debug("DONE");
    }

    // TODO 2 define another task at each second. This should not block.
    // TODO explore application.properties (thread #)
    public void pollFast() {
        log.debug("FAST each second");
    }
}
