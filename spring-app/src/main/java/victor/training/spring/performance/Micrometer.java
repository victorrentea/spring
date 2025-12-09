package victor.training.spring.performance;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.jvm.ExecutorServiceMetrics;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.ExecutorService;

@RequiredArgsConstructor
@RestController
public class Micrometer {
    private final JackpotRepo jackpotRepo;
    private final MeterRegistry micrometer;

    @EventListener(ApplicationStartedEvent.class)
    public void initGauge() {
        // TODO 3 monitor the global_jackpot with a 'gauge'
        micrometer.gauge("global_jackpot", jackpotRepo, JackpotRepo::getGlobalJackpot);
    }


    @GetMapping("/bet/{amount}")
    // TODO 2a report bet time with @Timed (Note: all REST endpoints are already metered by default)
    @Timed("bet_time") // or registry.timer()
    public String bet(@PathVariable int amount) {
        // TODO 1 report number of bets with a 'counter'
        micrometer.counter("bets").increment(amount);

        // TODO 4 report histogram of bets 'summary' + publish
        micrometer.summary("bets.histo").record(amount);

        jackpotRepo.addToGlobalJackpot(0.1 * amount);
        System.out.println("Bet " + amount);
        return UUID.randomUUID().toString();
    }

    @GetMapping("/bet/{uuid}/complete")
    public String betComplete(@PathVariable String uuid) {
        // TODO 2b report the time it took for users to complete a bet using micrometer's Timer
        return "🍀SENT";
    }

    @Repository
    static class JackpotRepo {
        private double globalJackpot; // kept in a DB

        public double getGlobalJackpot() {
            return globalJackpot;
        }

        public void addToGlobalJackpot(double amount) {
            globalJackpot += amount;
        }
    }


    @RequiredArgsConstructor
    @Configuration
    @EnableAsync
    public static class MonitoredThreadPoolConfig {
        private final MeterRegistry meterRegistry;

        @Bean
        public ExecutorService monitoredExecutor(ThreadPoolTaskExecutor taskExecutor) {
            // TODO 5 monitor the taskExecutor with ExecutorServiceMetrics
            return ExecutorServiceMetrics.monitor(meterRegistry, taskExecutor.getThreadPoolExecutor(), "taskExecutor");
        }

        @Bean // enables @Timed
        public TimedAspect timedAspect() {
            return new TimedAspect(meterRegistry);
        }
    }
}

