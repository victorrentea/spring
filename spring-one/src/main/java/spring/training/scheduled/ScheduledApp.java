package spring.training.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@Slf4j
@EnableScheduling
public class ScheduledApp implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(ScheduledApp.class);
    }

    @Override
    public void run(String... args) throws Exception {
        Thread.sleep(5000);
    }

    @Scheduled(fixedRateString = "${rate.millis}")
    // TODO should run every 1 seconds / configurable / cron
    public void lookIntoFolder() {
        log.debug("Looking into folder");
    }

    // mai bine pe termen lung este sa expui un endpoint HTTP in care sa dai din exterior cu curl schedulat dintr-un crontab.
    // daca vrei sa fii f secure: asigurate ca acel endpoint accepta doar req care vin de la localhost
}
