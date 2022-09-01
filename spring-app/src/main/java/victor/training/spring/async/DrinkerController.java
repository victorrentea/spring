package victor.training.spring.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
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

    // TODO [1] inject and submit work to a ThreadPoolTaskExecutor
    // TODO [2] mark pour* methods as @Async
    // TODO [3] Build a non-blocking web endpoint
    @GetMapping("api/drink")
    public DillyDilly drink() throws Exception {
        log.debug("Submitting my order");
        long t0 = currentTimeMillis();

        ExecutorService pool = Executors.newFixedThreadPool(2);
        Future<Beer> futureBeer = pool.submit(() -> barman.pourBeer());
        Future<Vodka> futureVodka = pool.submit(() -> barman.pourVodka());

        log.debug("Here, the waiter left with my 2 commands");

        Vodka vodka = futureVodka.get(); // how much time does the HTTP thread wait here? 1 sec
        Beer beer = futureBeer.get(); // how much time does the HTTP thread wait here? 0 sec


        long t1 = currentTimeMillis();
        log.debug("Got my drinks in {} millis", t1 - t0);
        return new DillyDilly(beer, vodka);
    }
}
