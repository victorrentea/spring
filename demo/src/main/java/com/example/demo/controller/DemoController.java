package com.example.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Slf4j
@RestController
public class DemoController {

    @Autowired
    private WelcomeInfo welcomeInfo;
    @Autowired
    private Barman barman;

    @GetMapping("welcome")
    public WelcomeInfo showWelcomeInfo() {
        return welcomeInfo;
    }

    @GetMapping("beu")
    public String beu() throws ExecutionException, InterruptedException {
        Future<String> viitoareBere = barman.toarnaBere();
        Future<String> viitoareVodca = barman.toarnaVodca();
        log.debug("Aici a plecat fata cu berea");
        // astea blocheaza threadul de HTTP request pentru  sec
        // Daca esti :( din cauza asta, vezi metoda de mai jos
        String bere = viitoareBere.get();
        String vodca = viitoareVodca.get();
        log.debug("Am primit beuturile");

        return bere +" cu " + vodca; // dilly dilly
    }

    @GetMapping("beu-eficient")
    public DeferredResult<String> beuEficient() throws ExecutionException, InterruptedException {
        CompletableFuture<String> viitoareBere = barman.toarnaBere();
        CompletableFuture<String> viitoareVodca = barman.toarnaVodca();
        log.debug("Aici a plecat fata cu berea");

        DeferredResult<String> deferredResult = new DeferredResult<>();

        CompletableFuture<String> futureDillyDilly = viitoareBere.thenCombineAsync(viitoareVodca, (bere, vodka) -> {
            log.debug("Am primit beuturile");
            return bere + " cu " + vodka; // dilly dilly
        });

        futureDillyDilly.thenAccept(dilly -> deferredResult.setResult(dilly));
// FATZA: sa nu consume threaduri aiurea
        log.debug("Threadul http iese aici");
        return deferredResult;

    }
}

@Slf4j
@Service
class Barman {
    @Async
    public CompletableFuture<String> toarnaBere() {
        try {
            log.debug("Torn bere");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return CompletableFuture.completedFuture("Bere");
    }
    @Async
    public CompletableFuture<String> toarnaVodca() {
        try {
            log.debug("Torn Vodca");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return CompletableFuture.completedFuture("Votca");
    }
}