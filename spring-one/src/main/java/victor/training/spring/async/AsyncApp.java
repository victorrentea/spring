package victor.training.spring.async;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskDecorator;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.ThreadUtils;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static java.lang.System.currentTimeMillis;

@EnableAsync
@EnableRetry
@SpringBootApplication
public class AsyncApp {

   @Bean
   public TimedAspect timedAspect() {
      return new TimedAspect();
   }

   public static void main(String[] args) {
      SpringApplication.run(AsyncApp.class, args); // Note: .close added to stop executors after CLRunner finishes
   }

   @Bean
   public ThreadPoolTaskExecutor executor(
         MeterRegistry registry,
          @Value("${barman.thread.count}") int barmanThreadCount) {
      ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
      executor.setCorePoolSize(barmanThreadCount);
      executor.setMaxPoolSize(barmanThreadCount);
      executor.setQueueCapacity(500);
      executor.setThreadNamePrefix("bar-");
      executor.initialize();
      executor.setTaskDecorator(new TaskDecorator() {
         @Override
         public Runnable decorate(Runnable taskulSubmis) {
            Timer timer = registry.timer("barman.queue.waiting");
            Timer taskTimer = registry.timer("barman.task.time");
            long t0 = currentTimeMillis(); // ruleaza atunci cand se face submit()
            // TODO iei de pe threadLocal sau @Scope("thread")   ca rulezi in threadul care face submit()
            return () -> {
               long t1 = currentTimeMillis(); // am inceput sa rulez
               // TODO repui pe ThreadLocal sau @Scope thread in threadul worker
               long waitingTimeInQueue = t1 - t0;
               timer.record(waitingTimeInQueue, TimeUnit.MILLISECONDS);

               taskulSubmis.run();
               long t2 = currentTimeMillis();
               long runTime = t2 - t1;
               taskTimer.record(runTime, TimeUnit.MILLISECONDS);
            };
         }
      });
      executor.setWaitForTasksToCompleteOnShutdown(true);
      return executor;
   }

}

@Slf4j
@RestController
class Drinker {
   @Autowired
   private Barman barman;
//
//	@Autowired
//	ThreadPoolTaskExecutor executor;

   // TODO [1] inject and use a ThreadPoolTaskExecutor.submit
   // TODO [2] make them return a CompletableFuture + @Async + asyncExecutor bean
   // TODO [3] Messaging...
//	@PostConstruct
//	@EventListener(ApplicationContextInitializedEvent.class)
//	public void method() {
   @GetMapping("drink")
   public CompletableFuture<String> run() throws Exception {
      log.debug("Submitting my order to " + barman.getClass());

      CompletableFuture<Beer> futureBeer = barman.getOneBeer();
      CompletableFuture<Vodka> futureVodka = barman.getOneVodka();

      barman.injura("&^$!&^&@!^%!*@*$&@!");

      log.debug("AICI in paralel se pornesc 2 executii pe threaduri separate");

//		Beer beer = futureBeer.get();
//		Vodka vodka = futureVodka.get();

      CompletableFuture<String> futureString = futureBeer.thenCombine(futureVodka, (beer, vodka) -> beer + " cu " + vodka);
      log.debug("Got my order! Thank you lad! AICI PLEC ");
      return futureString;
   }

   @GetMapping("explicatie")
   public void CumMergeInSpate(HttpServletRequest request) throws Exception {
      log.debug("INTRU");
      AsyncContext asyncContext = request.startAsync();
      // requestul ramane deschi, nu se inchide TCP conncetion

      run().thenAccept(s -> {
         scrie(asyncContext);
      });
      log.debug("IES!");
   }

   private void scrie(AsyncContext asyncContext) {
      log.debug("SCRIU RASPUNS");
      try {
         asyncContext.getResponse().getWriter().write("SALUT!");
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
      asyncContext.complete();
   }
}

@Slf4j
@Service
class Barman {
   @Async
//@Retryable()
   public CompletableFuture<Beer> getOneBeer() {
      log.debug("Pouring Beer...");
      ThreadUtils.sleep(1000);
//		 new RestTemplate().getForObject("http://alta.app.intra.unicredit:/adsa", String.class);
      return CompletableFuture.completedFuture(new Beer());
   }

   @Timed("vodka.time")
   @Async/*("threadPoolCu1Thread")*/
   public CompletableFuture<Vodka> getOneVodka() {
      log.debug("Pouring Vodka...");
      ThreadUtils.sleep(1000); // endpoint de guvern
      return CompletableFuture.completedFuture(new Vodka());
   }

   @Async
	public void injura(String uratura) {
		if (uratura != null) {
			throw new IllegalArgumentException("Iti fac buzunar");
		}
	}
}

@Data
class Beer {
   public final String type = "blond";
}

@Data
class Vodka {
   public final String type = "deadly";
}
