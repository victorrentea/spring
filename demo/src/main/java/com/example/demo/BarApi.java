package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class BarApi {
  private static final Logger log = LoggerFactory.getLogger(BarApi.class);
  private final Barman barman;

  public BarApi(Barman barman, ThreadPoolTaskExecutor poolBar) {
    this.barman = barman;
  }

  @GetMapping("api/drink")
  public Mono<DillyDilly> drink() throws Exception {
    log.info("Submitting my order");
    Mono<Beer> beerMono = barman.pourBeer();
    Mono<Vodka> vodkaMono = barman.pourVodka();
    return beerMono.zipWith(vodkaMono, DillyDilly::new);
  }

  @GetMapping("api/beer/{type}")
  public Beer beer(@PathVariable String type) throws InterruptedException {
    log.info("On the other microservice (pretend)");
    Thread.sleep(1000);// excuse
    return new Beer(type);
  }

  @GetMapping("api/vodka")
  public Vodka vodka() throws InterruptedException {
    Thread.sleep(1000);// excuse
    return new Vodka();
  }
}