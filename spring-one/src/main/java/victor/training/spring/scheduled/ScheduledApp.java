package victor.training.spring.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import victor.training.spring.ThreadUtils;
import victor.training.spring.web.SpaApplication;

@SpringBootApplication
@Slf4j
@EnableScheduling
public class ScheduledApp  {
    public static void main(String[] args) {
        new SpringApplicationBuilder(ScheduledApp.class)
            .profiles("spa")
            .run(args);
    }

    // TODO 1 Should run every 5 seconds / configurable / cron "*/5 * * * * *"
    // TODO 3 Play with delays. cron vs fixedRate? Overlapping executions?
    // TODO 4 Should run on a separate 1-thread pool

    @Scheduled(fixedRateString = "${poll.rate:300}")
    public void lookIntoFolder() {
        log.debug("Looking into folder");
        ThreadUtils.sleep(7000);
        log.debug("DONE");
    }
    @Scheduled(cron = "*/5 * * * * *")
    public void lookIntoFolder2() {
        log.debug("Looking into folder poll");
        log.debug("DONE");
    }

    // considera ca in loc sa faci tu intern
    // un scheduler sa pornesti executia la ore fixe ruland un cron pe UNIX care sa dea
    // cu curl -X POST http://localhost:8080/job/importCountries/start

    // daca treba dureaza mult, lanseaz-o pe un thread pool doar al lui cu @Async

    // quartz - e si persistat intr-un DB, ai evidenta ce a rulat, cand, cu ce rezultat, etc.

    // TODO 2 define another task at each second. This should not block.
    // TODO explore application.properties (thread #)
    public void pollFast() {
        log.debug("FAST each second");
    }
}
