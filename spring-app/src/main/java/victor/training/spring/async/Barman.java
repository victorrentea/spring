package victor.training.spring.async;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jcajce.provider.symmetric.AES.CFB;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import victor.training.spring.async.drinks.Beer;
import victor.training.spring.async.drinks.Vodka;
import victor.training.spring.varie.ThreadUtils;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class Barman {
    public Beer pourBeer() {
        log.debug("Pouring Beer (SOAP/WSDL -guvern -dark CALL)...");
        ThreadUtils.sleepq(1000);
        return new Beer();
    }

    public Vodka pourVodka() {
        log.debug("Pouring Vodka (REST CALL), export din DB SQL heavy...");
        ThreadUtils.sleepq(1000);
        return new Vodka();
    }

    @Async
    public CompletableFuture<Void> injura(String uratura) {
        log.info("AICI");
        throw new IllegalArgumentException("Iti fac buzunar!");

    }
}
