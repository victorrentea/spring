package victor.training.spring.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.async.Barman;
import victor.training.spring.async.Beer;
import victor.training.spring.async.DillyDilly;
import victor.training.spring.async.Vodka;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequestMapping("async")
@RequiredArgsConstructor
public class AsyncController {
   private final Barman barman;
   @GetMapping
   public String bea() throws ExecutionException, InterruptedException {

      CompletableFuture<Beer> futureBeer = barman.getOneBeer();
      CompletableFuture<Vodka> futureVodka = barman.getOneVodka();

      CompletableFuture<DillyDilly> futureDilly = futureBeer
          .thenCombine(futureVodka, DillyDilly::new);

//      futureDilly.thenAccept(dilly -> log.debug("Got my order! Thank you lad! " + dilly));

      String message = "Savurez " + futureDilly.get();
      log.debug("Main-ul pleaca acasa");
      return message;
   }
}
