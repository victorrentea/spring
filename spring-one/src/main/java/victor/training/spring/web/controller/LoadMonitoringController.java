package victor.training.spring.web.controller;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.LongTaskTimer;
import io.micrometer.core.instrument.LongTaskTimer.Sample;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.ThreadUtils;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LoadMonitoringController {
   private final ExpensiveApiClient apiClient;

   @GetMapping("load/expensive")
   public CompletableFuture<String> expensive() {
      return apiClient.call();
   }

}

@RequiredArgsConstructor
@Configuration
@EnableAsync
class CustomThreadPool {
   private final MeterRegistry meterRegistry;

   @Bean(initMethod = "initialize")
   @ConfigurationProperties(prefix = "custom-executor")
   public ThreadPoolTaskExecutor customExecutor() {
      Timer timer = meterRegistry.timer("custom-executor-queue-wait");
      // configured via custom-executor.* in application.properties
      ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
      executor.setTaskDecorator(runnable -> {
             long t0 = System.currentTimeMillis(); // runs at submit() time
             return () -> {
                long t1 = System.currentTimeMillis(); // runs when task start being executed
                timer.record(t1 - t0, TimeUnit.MILLISECONDS);
                runnable.run();
             };
          });
      return executor;
   }

   @Bean
   public TimedAspect timedAspect() {
      return new TimedAspect(meterRegistry);
   }
}


@Slf4j
@RequiredArgsConstructor
@Service
class ExpensiveApiClient {
   @Async("customExecutor")
   @Timed("external call")
   public CompletableFuture<String> call() {
      log.info("Calling...");
      ThreadUtils.sleep(1000);
      log.info("DONE");
      return CompletableFuture.completedFuture("data");
   }
}

