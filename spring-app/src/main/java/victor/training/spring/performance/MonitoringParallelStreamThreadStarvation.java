package victor.training.spring.performance;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.jvm.ExecutorServiceMetrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

@RestController
public class MonitoringParallelStreamThreadStarvation {
   @Autowired
   MeterRegistry registry;

   @PostConstruct
   public void method() {
      ExecutorServiceMetrics.monitor(registry, ForkJoinPool.commonPool(), "commonPool");
   }

   @GetMapping("test")
   public void get() {
      IntStream.range(1,100).parallel().forEach(System.out::println);
   }
}
