package victor.training.spring.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class ScheduledApp implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(ScheduledApp.class);
    }

    @Override
    public void run(String... args) throws Exception {
        Thread.sleep(5000);
    }

    // TODO should run every 5 seconds / configurable / cron
    public void lookIntoFolder() {
        log.debug("Looking into folder");
    }
}
