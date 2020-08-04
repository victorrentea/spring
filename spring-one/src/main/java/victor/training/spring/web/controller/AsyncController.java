package victor.training.spring.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.ThreadUtils;

import java.util.concurrent.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AsyncController {
   private final JobCareDureazaMult job1;
   private final JobCareDureazaMult2 job2;

   static ExecutorService pool = Executors.newFixedThreadPool(2);

   @GetMapping("job")
   public String ruleazaDoua() throws ExecutionException, InterruptedException {
      Future<String> job1Result = pool.submit(() -> job1.m());
      Future<String> job2Result = pool.submit(() -> job2.m());

      log.debug("Am pornit ambele joburi");
      String status1 = job1Result.get(); // cat astepti la linia asta 3 sec
      String status2 = job2Result.get();// cat astepti la linia asta 0 sec

      log.debug("Gata Ambele");
      return "Rezultate: " + status1 + " si " + status2;
   }

}


@Component
@Slf4j
class JobCareDureazaMult {
   public String m() {
      log.debug("Start");
      ThreadUtils.sleep(3000);
      log.debug("End");
      return "SUCCESS";
   }
}
@Component
@Slf4j
class JobCareDureazaMult2 {
   public String m() {
      log.debug("Start2");
      ThreadUtils.sleep(3000); // REST, DB QUERY NASPA, S3, CPU(criptari)
      log.debug("End2");
      return "null";
   }
}