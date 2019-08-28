package victor.training.spring.schedulers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import victor.training.spring.ThreadUtils;

import static victor.training.spring.ThreadUtils.sleep;

@Slf4j
@EnableScheduling
@SpringBootApplication
public class SchedulersApp implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(SchedulersApp.class);
    }

//    @Scheduled(fixedRate = 1000)
//    @Scheduled(fixedDelay = 1000)
    @Scheduled(cron = "${my.cron}")
//    @Scheduled(cron = "0/1 * * * * *")
    public void aVenitCevaInFolder() {
        log.debug("start");
        sleep(300);
        log.debug("end");
    }

    @Async
    public void faTreaba() {

    }

    @Override
    public void run(String... args) throws Exception {
        sleep(5000);
    }
}
