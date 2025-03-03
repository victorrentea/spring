package victor.training.spring.async;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.async.drinks.Beer;
import victor.training.spring.async.drinks.DillyDilly;
import victor.training.spring.async.drinks.Vodka;

import javax.print.Doc;

import static java.lang.System.currentTimeMillis;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BarApi {
   private final Barman barman;

   @GetMapping("api/drink")
   public DillyDilly drink() throws Exception {
      log.debug("Submitting my order");
      long t0 = currentTimeMillis();

      Beer beer = barman.pourBeer();
      Vodka vodka = barman.pourVodka();
      // #1 sequential calls that are independent

      // #2: why do I wait for email to be sent? > run in back ground
      // #3: failures on Email server are not critical
      barman.sendNotification("Dilly");

      //a) Kafka/Rabbit send instead 💖
      //b) store this notification in a db (outbox table pattern) to poll afterwards

      log.debug("HTTP thread released in {} millis", currentTimeMillis() - t0);
      return new DillyDilly(beer, vodka);
   }

//   @PostMapping
//   public Mono<Void> saveToDb() {
//      Mono<Void> shit = reactiveRepo.save(new Doc());
//      // shit.block(); // illegal bcause it hijacks the non-blocking nature of the beast - the very reason that Picnic chose Reactive paradigm
//      // shit.subscribe(); // BAD PRACTICE because it doesnt propagate security context @Secured won't work
//      return shit; // the correct way
//   }
}
