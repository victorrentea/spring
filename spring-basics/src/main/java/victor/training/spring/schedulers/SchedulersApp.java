package victor.training.spring.schedulers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import victor.training.spring.ThreadUtils;

import static victor.training.spring.ThreadUtils.sleep;

@Slf4j
@EnableScheduling
@EnableAsync
@SpringBootApplication
public class SchedulersApp implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(SchedulersApp.class);
    }

    @Bean
    public ThreadPoolTaskScheduler customScheduler () {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(2);
        scheduler.setThreadGroupName("sched");
        return scheduler;
    }

//    @Scheduled(fixedRate = 1000)
//    @Scheduled(fixedDelay = 1000)
    @Scheduled(cron = "${my.cron}")
//    @Scheduled(fixedDelayString = "${dinProp}")
//    @Scheduled(cron = "0/1 * * * * *")
    public void aVenitCevaInFolder() {
        spring.getBean(SchedulersApp.class).faTreaba();
    }

    @Autowired
    ApplicationContext spring;

    @Async/*("threadPoolBeanName")*/
    public void faTreaba() {
        log.debug("start1");
        sleep(4000);
        log.debug("end1");
    }

    @Scheduled(cron = "${my.cron}")
    public void aVenitCevaInFolder2() {
        log.debug("start2");
        sleep(2000);
        log.debug("end2");
    }

    @Override
    public void run(String... args) throws Exception {
        sleep(5000);
    }
}
