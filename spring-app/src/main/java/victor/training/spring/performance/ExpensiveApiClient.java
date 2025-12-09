package victor.training.spring.performance;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import victor.training.spring.varie.Sleep;

import java.util.concurrent.CompletableFuture;

@Slf4j
@RequiredArgsConstructor
@Service
public class ExpensiveApiClient {
    @Async("customExecutor")
    @Timed("external call")
    public CompletableFuture<String> asyncCall() {
        log.info("Calling async...");
        Sleep.millis(1000);
        log.info("DONE");
        return CompletableFuture.completedFuture("data");
    }

    public String blockingCall() {
        log.info("Calling sync...");
        Sleep.millis(1000);
        log.info("DONE");
        return "data " + System.currentTimeMillis();
    }
}
