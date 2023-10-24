package victor.training.spring.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import victor.training.spring.async.drinks.Beer;
import victor.training.spring.async.drinks.DillyDilly;
import victor.training.spring.async.drinks.Vodka;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static java.lang.System.currentTimeMillis;

@Slf4j
@RestController
public class DrinkerController {
   @Autowired
   private Barman barman;
   @Autowired
   private RestTemplate restTemplate;

   @Autowired
   private ThreadPoolTaskExecutor executor;

   // TODO [1] autowire and submit tasks to a ThreadPoolTaskExecutor
   // TODO [2] mark pour* methods as @Async
   // TODO [3] Make this endpoint non-blocking
   @GetMapping("api/drink")
   public DillyDilly drink() throws Exception {
      log.debug("Submitting my order " + restTemplate.getClass());
      long t0 = currentTimeMillis();

      // How to lose the TraceID on outbound request/message:
      // Pitfall#1:
      //new RestTemplate().getForObject()// this call does not carry the request header!!! instead use a Spring-injected RestTemplate/WebClient/RabbitTemplate...

//      restTemplate.getForObject// the http request will carry automatcally the TraceId to all other microservices that I call
//      rabbitTemplate.send(message// the message will carry automatcally the TraceId to all other microservices that I call

      // Pitfall#2: move to another thread without Spring awareness (using a custom-made executor/thread)
//      ExecutorService threadPool = Executors.newFixedThreadPool(3); // DO NOT EVER USE Executors in a Spring application

      Future<Beer> beerF = executor.submit(()->barman.pourBeer());
      Future<Vodka> vodkaF = executor.submit(()->barman.pourVodka());

      executor.submit(()->barman.auditCocktail("Dilly"));// fire-and-forget call: start but never wait to complete/throw

      Beer beer = beerF.get();
      Vodka vodka = vodkaF.get();
      DillyDilly dillyDilly = new DillyDilly(beer, vodka);

      log.debug("Method completed in {} millis", currentTimeMillis() - t0);
      return dillyDilly;
   }

}
