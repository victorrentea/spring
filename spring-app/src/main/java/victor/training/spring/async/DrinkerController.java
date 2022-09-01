package victor.training.spring.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.async.drinks.Beer;
import victor.training.spring.async.drinks.DillyDilly;
import victor.training.spring.async.drinks.Vodka;

import java.util.concurrent.CompletableFuture;
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
    private ThreadPoolTaskExecutor barExecutor;
    private static final ExecutorService pool = Executors.newFixedThreadPool(4);

    // TODO [1] inject and submit work to a ThreadPoolTaskExecutor
    // TODO [2] mark pour* methods as @Async
    // TODO [3] Build a non-blocking web endpoint
    @GetMapping("api/drink")
    public CompletableFuture<DillyDilly> drink() throws Exception {
        log.debug("Submitting my order");
        long t0 = currentTimeMillis();

        log.debug("Here, the waiter left with my 2 commands");

        CompletableFuture<Beer> beerCF = CompletableFuture.supplyAsync(() -> barman.pourBeer());
        CompletableFuture<Vodka> vodkaCF = CompletableFuture.supplyAsync(() -> barman.pourVodka());


        CompletableFuture<DillyDilly> dillyCF = beerCF.thenCombineAsync(vodkaCF,
                (beer, vodka) -> new DillyDilly(beer, vodka));

        barExecutor.submit(() -> barman.curse("$!#%!*%&!&*!")); // fire-and-forget action

        long t1 = currentTimeMillis();
        log.debug("Got my drinks in {} millis", t1 - t0);
        return dillyCF;
    }
}
