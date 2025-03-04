package com.example.demo;

import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Timed
public class Barman {
  private static final Logger log = LoggerFactory.getLogger(Barman.class);
  private final WebClient webClient;

  public Barman(WebClient webClient) {
    this.webClient = webClient;
  }

  public Mono<Beer> pourBeer() {
    log.info("Fetching Beer...");
    return webClient.get()
        .uri("http://localhost:8080/api/beer/{type}", "blond")
        .retrieve()
        .bodyToMono(Beer.class)
        .delayUntil(b-> Mono.deferContextual(context -> {
          log.info("Requesting for user {}", (String) context.get("username"));
          return Mono.empty();
        }));
  }

  public Mono<Vodka> pourVodka() {
    log.info("Fetching Vodka...");
    return webClient.get()
        .uri("http://localhost:8080/api/vodka")
        .retrieve()
        .bodyToMono(Vodka.class);
  }

//  @Async("poolBar")// HUH!?!?
//  public void sendNotification(String email) { // TODO outbox pattern
//    log.debug("Sending notification (takes time and might fail) {}...", email);
//    Sleep.millis(500); // critical but slow work that can fail
//    if (Math.random() < 0.5) throw new RuntimeException("Email server down");
//    log.debug("Notification sent!");
//  }

}