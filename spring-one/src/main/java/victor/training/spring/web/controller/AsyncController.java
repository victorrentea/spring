package victor.training.spring.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import victor.training.spring.ThreadUtils;

import java.util.concurrent.*;

import static java.util.concurrent.CompletableFuture.completedFuture;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AsyncController {
   private final JobCareDureazaMult job1;
   private final JobCareDureazaMult2 job2;

   @Bean
   public ThreadPoolTaskExecutor poolPentruAiaFragili(@Value("${pool.size:1}") int poolSize) {
      ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
      pool.setMaxPoolSize(poolSize);
      pool.setCorePoolSize(poolSize);
      return pool;
   }

   static ExecutorService pool = Executors.newFixedThreadPool(2);

   @GetMapping("job")
   public DeferredResult<String> ruleazaDoua() throws ExecutionException, InterruptedException {
      DeferredResult<String> deferred = new DeferredResult<>();
      log.info("Oare cui trimit eu apelul meu de functie? " + job1.getClass());
      CompletableFuture<String> job1Result = job1.m();
      CompletableFuture<String> job2Result = job2.m();



      CompletableFuture<String> finalResult = job1Result.thenCombineAsync(job2Result, (j1, j2) -> "Rezultate: " + j1 + " si " + j2);
      // finalResult devine gata dupa ce ambele promiserui sunt gata
      finalResult.thenAcceptAsync(r -> deferred.setResult(r));
      log.debug("Ma intorc langa fratii mei, in piscina");
      return deferred;
//

//      log.debug("Am pornit ambele joburi");
//      String status1 = job1Result.get(); // cat astepti la linia asta 3 sec
//      String status2 = job2Result.get();// cat astepti la linia asta 0 sec
//      log.debug("Gata Ambele");
//      return "Rezultate: " + status1 + " si " + status2;
   }

   @GetMapping("extern")
   public String callExtern() throws ExecutionException, InterruptedException {
      return job1.m().get();

   }
}
@Component
@Slf4j
class JobCareDureazaMult {
   @Async("poolPentruAiaFragili") //thread poolul de aici cat de mare tre sa fie 300 - DDOS
   public CompletableFuture<String> m() {
      log.debug("Start");
      ThreadUtils.sleep(3000); // REST Call catre api-ul echipe adverse
      log.debug("End");
      return completedFuture("SUCCESS");
   }
}
@Component
@Slf4j
class JobCareDureazaMult2 {
   @Async
   public CompletableFuture<String> m() {
      log.debug("Start2");
      ThreadUtils.sleep(3000); // REST, DB QUERY NASPA, S3, CPU(criptari)
      log.debug("End2");
      return completedFuture("null");
   }
}