package victor.training.spring.async;

import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;
import victor.training.spring.ThreadUtils;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

@EnableAsync
@SpringBootApplication
public class AsyncApp {
    public static void main(String[] args) {
        SpringApplication.run(AsyncApp.class, args);//.close(); // Note: .close added to stop executors after CLRunner finishes
        ThreadUtils.sleep(5000);
    }

}

@Slf4j
@Component
class Drinker implements CommandLineRunner {
    @Autowired
    private Barman barman;

    // TODO [1] inject and use a ThreadPoolTaskExecutor.submit
    // TODO [2] make them return a CompletableFuture + @Async + asyncExecutor bean
    // TODO [3] Enable messaging...
    public void run(String... args) throws Exception {
        CompletableFuture<String> viitoareBere = barman.toarnaBere();
        CompletableFuture<String> viitoareVodca = barman.toarnaVodca();
        log.debug("Aici a plecat fata cu berea");

        CompletableFuture<String> futureDillyDilly = viitoareBere.thenCombineAsync(viitoareVodca, (bere, vodka) -> {
            log.debug("Am primit beuturile");
            return bere + " cu " + vodka; // dilly dilly
        });
        futureDillyDilly.thenAccept(dilly -> log.debug("Consum " + dilly));

        log.debug("Threadul http iese aici");
    }
}

@Slf4j
@Service
class Barman {
    @SneakyThrows
    @Async
    public CompletableFuture<String> toarnaBere() {
        log.debug("Torn bere");
        Thread.sleep(2000);
        return CompletableFuture.completedFuture("Bere");
    }

    @SneakyThrows
    @Async
    public CompletableFuture<String> toarnaVodca() {
        log.debug("Torn Vodca");
        Thread.sleep(2000);
        return CompletableFuture.completedFuture("Votca");
    }
}
