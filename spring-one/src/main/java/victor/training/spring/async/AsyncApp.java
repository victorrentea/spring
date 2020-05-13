package victor.training.spring.async;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import victor.training.spring.ThreadUtils;

@EnableBinding(Queues.class)
@EnableAsync
@SpringBootApplication
public class AsyncApp {
    public static void main(String[] args) {
        SpringApplication.run(AsyncApp.class, args);//.close(); // Note: .close added to stop executors after CLRunner finishes
        ThreadUtils.sleep(5000);
    }

}

@Slf4j
@Component
class Drinker implements CommandLineRunner {
    private final MessageChannel beerRequestsOut;
    private final MessageChannel vodkaRequestsOut;

    Drinker(MessageChannel beerRequestsOut, MessageChannel vodkaRequestsOut) {
        this.beerRequestsOut = beerRequestsOut;
        this.vodkaRequestsOut = vodkaRequestsOut;
    }

    public void run(String... args) {
        String x = "xx;";
        beerRequestsOut.send(MessageBuilder.withPayload("blonda craft").build());
        vodkaRequestsOut.send(MessageBuilder.withPayload("tare").build());
        log.debug("Aici a plecat fata cu berea");

        // NU MA MAI POT BLOCA AICI.

        // DESIGN: fluxul in mijlocul caruia vrei sa schimbi un mesaj cu alt ssitem
        // trebuie rupt in doua, iar starea procesului de la momentul trimiterii,
        // ca s-o folosesti la primire, trebuie PERSISTATA undeva. DB*

        ////
//        CompletableFuture<String> futureDillyDilly = viitoareBere.thenCombineAsync(viitoareVodca, (bere, vodka) -> {
//            log.debug("Am primit beuturile");
//            return bere + " cu " + vodka; // dilly dilly
//        });
//        futureDillyDilly.thenAccept(dilly -> log.debug("Consum " + dilly + " + x));
//
//        log.debug("Threadul http iese aici");
    }

    @ServiceActivator(inputChannel = Queues.DRINKS_RESULT_IN)
    public void drink(String drink) {
        log.debug("Eu beu: " + drink);
    }
}

// intro-alta aplicatie, peste mari si tzari.

@Slf4j
@Service
class Barman {
    private final MessageChannel responseChannel;

    public Barman(@Qualifier(Queues.DRINKS_RESULT_IN) MessageChannel responseChannel) {
        this.responseChannel = responseChannel;
    }

    @ServiceActivator(inputChannel = Queues.BEER_REQUESTS_IN)
    @SneakyThrows
    public void toarnaBere(String tipBere) {
        log.debug("Torn bere");
        Thread.sleep(2000);
        responseChannel.send(MessageBuilder.withPayload("Bere "+ tipBere).build());
    }

    @ServiceActivator(inputChannel = Queues.VODKA_REQUESTS_IN)
    @SneakyThrows
    public void toarnaVodca() {
        log.debug("Torn Vodca");
        Thread.sleep(2000);
        responseChannel.send(MessageBuilder.withPayload("Vodka tare").build());
    }
}
