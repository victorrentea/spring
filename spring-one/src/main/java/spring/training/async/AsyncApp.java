package spring.training.async;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import spring.training.ThreadUtils;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import static java.util.concurrent.CompletableFuture.completedFuture;

@EnableAsync
@SpringBootApplication
public class AsyncApp {
    public static void main(String[] args) {
        SpringApplication.run(AsyncApp.class, args).close(); // Note: .close to stop executors after CLRunner finishes
    }

    @Bean
    public ThreadPoolTaskExecutor executor(@Value("${barman.thread.count}") int threadCount) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(threadCount);
        executor.setMaxPoolSize(threadCount);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("bar-");
        executor.initialize();
        executor.setWaitForTasksToCompleteOnShutdown(true);
        return executor;
    }
    @Bean
    public ThreadPoolTaskExecutor customExecutor(@Value("${poli.thread.count:100}") int threadCount) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(threadCount);
        executor.setMaxPoolSize(threadCount);
        executor.setQueueCapacity(500);
        //pub.ro
        executor.setThreadNamePrefix("poli-");
        executor.initialize();
        executor.setWaitForTasksToCompleteOnShutdown(true);
        return executor;
    }

}

@Slf4j
@Component
class Drinker implements CommandLineRunner {
    @Autowired
    private Barman barman;

    // TODO [1] inject and use a ThreadPoolTaskExecutor.submit
    // TODO [2] make them return a CompletableFuture + @Async + asyncExecutor bean
    public void run(String... args) throws Exception {
        Thread.sleep(3000);
        log.debug("Submitting my order");

        Future<Ale> futureAle = barman.getOneAle();
        Future<Ale> futureAle2 = barman.getOneAle();
        Future<Ale> futureAle3 = barman.getOneAle();
        Future<Ale> futureAle4 = barman.getOneAle();
        Future<Ale> futureAle5 = barman.getOneAle();
        Future<Ale> futureAle6 = barman.getOneAle();
        Future<Ale> futureAle7 = barman.getOneAle();
        Future<Whiskey> futureWhiskey = barman.getOneWhiskey();
        log.debug("A plecat fata cu comenzile (2)");
        Ale ale = futureAle.get();
        Whiskey whiskey = futureWhiskey.get();
        log.debug("Got my order! Thank you lad! " + Arrays.asList(ale, whiskey));

        barman.injural("!#!$!@!#!%!@#!@%^!@#");
        log.debug("Plec acasa linistit");
    }
}

@Slf4j
@Service
class Barman {
    @Async("customExecutor") // pe ce executor vreau sa ruleze.
    public Future<Ale> getOneAle() {
        log.debug("Pouring Ale...");
        ThreadUtils.sleep(1000);
        if (Math.random() < 0.5) {
            throw new IllegalStateException("Nu mai e bere !!!");
        }
        return completedFuture(new Ale());
    }

    @Async
    public Future<Whiskey> getOneWhiskey() {
        log.debug("Pouring Whiskey...");
        ThreadUtils.sleep(1000);
        return completedFuture(new Whiskey());
    }


    @Async
    public void injural(String urare) {
        if (urare != null) {
            throw new IllegalArgumentException("Iti fac buzunar");
        }
    }
}

@Data
class Ale {
}

@Data
class Whiskey {
}
