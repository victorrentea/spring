package victor.training.spring.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import victor.training.spring.async.drinks.Beer;
import victor.training.spring.async.drinks.Vodka;
import victor.training.spring.varie.ThreadUtils;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class Barman {
    @Async("bar")
    public CompletableFuture<Beer> pourBeer() {
        log.debug("Pouring Beer (SOAP/WSDL -guvern -dark CALL)...");
//        if (true) {
//            throw new IllegalStateException("NU mai e bere!");
//        }
        ThreadUtils.sleepq(1000);
        return CompletableFuture.completedFuture(new Beer());
    }

    public Vodka pourVodka() {
        log.debug("Pouring Vodka (REST CALL), export din DB SQL heavy...");
        ThreadUtils.sleepq(1000);
        return new Vodka();
    }

    // vrei sa startezi printr-un req http un proces ce dureaza minute.
    // nu poti/vrei sa tii http req blocat 1 min.
    // chemi o metoda @Async ce intoarce void din endpointul tau
    // = "Fire-and-forget pattern"
    // @Async + void = ❤️ Springu automat logheaza exceptii aparute in log
    @Async("bar") // poti sa-l pui pe "bar" ThrPTExecutor
    public void injura(String uratura) {
        log.info("AICI");
        throw new IllegalArgumentException("Iti fac buzunar!");
    }
}
