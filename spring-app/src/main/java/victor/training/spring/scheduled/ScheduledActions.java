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
    // TODO 4 Should run on a separate 1-thread barPool
    @Scheduled(cron = "*/5 * * * * *")
    public void lookIntoFolder() {
        // what can you do with stuff that runs
        // 1 clean a local cache: every minute you wipe the entire cache out.
            // no entry should be older than 1 minute.
            // not optimal: what if in second #59 you just brought a product by ID
            // better: have each entry timestamped and remove autmatically:
            // for example: https://www.ehcache.org/documentation/2.7/configuration/data-life.html

        // 2 "retention policy in the database"
            // every 10 mins we delete entries that have creation time > 1 hour ago.

        // 3 pull some data from some other service (eg every  Sun night on full moon, we bring the supported currencies.
            // every night we bring the FEX

        // 4 alive checks "Health" are you up for an external API?
            //  If it's NOT up : send email? alert
            //            If it's under your control, you use Spring Boot Actuator

        ThreadUtils.sleepq(7000);
        log.debug("DONE");
    }

    // TODO 2 define another task at each second. This should not block.
    // TODO explore application.properties (thread #)
    public void pollFast() {
        log.debug("FAST each second");
    }
}
