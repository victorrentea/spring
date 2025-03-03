package victor.training.spring.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.async.drinks.Beer;
import victor.training.spring.async.drinks.DillyDilly;
import victor.training.spring.async.drinks.Vodka;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import static java.lang.System.currentTimeMillis;

@Slf4j
@RestController
public class BarApi {
  private final Barman barman;
  private final ThreadPoolTaskExecutor poolBar;

  public BarApi(Barman barman, ThreadPoolTaskExecutor poolBar) {
    this.barman = barman;
    this.poolBar = poolBar;
  }

  @GetMapping("api/drink")
  public DillyDilly drink() throws Exception {
    log.debug("Submitting my order");
    long t0 = currentTimeMillis();

    Future<Beer> beerFuture = poolBar.submit(() -> barman.pourBeer());
    Future<Vodka> vodkaFuture = poolBar.submit(() -> barman.pourVodka());

    Beer beer = beerFuture.get();// blocking
    Vodka vodka = vodkaFuture.get();// blocking

    // #1 sequential calls that are independent
    // WebFlux: mono1.zipWith(mono2, (beer, vodka) -> new DillyDilly(beer, vodka))

    // #2: why do I wait for email to be sent? > run in back ground
    // #3: failures on Email server are not critical
    // Solution#1: "Fire and forget" [in memory] with:
    //1) threadPool.submit
    //2) CompletableFuture.runAsync(() -> barman.sendNotification("Dilly"), poolBar); 💖
    barman.sendNotification("Dilly"); // call an @Async annotated method
    //4) WebFlux: someService.reactiveMethod(..).subscribe();
    // !!! check that you propagate request metadata (traceId, securityContext)


    // !!! how to manage background errors ? scrap log, alarms,
    // !!! what if my server crashes with items in the waiting queue?
    // Fix:
    // Solution#2: "Fire and forget" persisted
    //a) Kafka/Rabbit send instead 💖 (durable queue)
    //b) store this notification in a db (outbox table pattern) to poll afterwards every 5 sec (eg)

    log.debug("HTTP thread released in {} millis", currentTimeMillis() - t0);
    return new DillyDilly(beer, vodka);
  }


//  @Scheduled(cron = "0/5 * * * * *")// every 5 seconds cron
  @Scheduled(fixedRateString = "${outbox.poll.ms}")// adjust rate via config
  public void method() { // runs every 5 sec
    // 1) what if it takes > 5 secods (rate): they will NEVER overlap?
    // 2) two instance (pods) of this app will race eachother: scheduled will run on both.
    // fix:
    // a) enable scheduled on only ONE instance @Profile or @ConditionalOnProperty = risky
    // b) distributed locking 💖(Redis locks, SQL-level locks: SELECT FOR UPDATE) @ShedulerLock// https://www.baeldung.com/shedlock-spring
    // c) introduce random skew -@sander wants to take chances. risky
    // d) SELECT from INBOX where id % 2 = 0: SPOF + autoscale=hell
    // e) replace @Scheduler with @GetMapping/@Kafka|RabbitListener and have an external cron job fire a call/message
    //    this call will be load balanced by the gateway and will run only on one instance
    //    easier to test/manual call💖💖💖
    log.info("Looking in the OUTBOX table for notifications to POST to the email server");
  }


//   @PostMapping
//   public Mono<Void> saveToDb() {
//      Mono<Void> shit = reactiveRepo.save(new Doc());
//      // shit.block(); // illegal bcause it hijacks the non-blocking nature of the beast - the very reason that Picnic chose Reactive paradigm
//      // shit.subscribe(); // BAD PRACTICE because it doesnt propagate security context @Secured won't work
//      return shit; // the correct way
//   }
}
