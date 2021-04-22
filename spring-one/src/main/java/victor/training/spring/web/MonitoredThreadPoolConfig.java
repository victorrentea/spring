package victor.training.spring.web;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@RequiredArgsConstructor
@Configuration
@EnableAsync
public class MonitoredThreadPoolConfig {
   private final MeterRegistry meterRegistry;

   @Bean
   public TimedAspect timedAspect() {
      return new TimedAspect(meterRegistry);
   }
}
