package com.example.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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
        CompletableFuture<String> viitoareBere = barman.toarnaBere();
        CompletableFuture<String> viitoareVodca = barman.toarnaVodca();
        log.debug("Aici a plecat fata cu berea");
        String bere = viitoareBere.get();
        String vodca = viitoareVodca.get();
        log.debug("Am primit beuturile");

        return bere +" cu " + vodca; // dilly dilly
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