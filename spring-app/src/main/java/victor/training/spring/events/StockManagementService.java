package victor.training.spring.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StockManagementService {
    private int stock =0; // silly implem :D

    @EventListener // N-O FACE⚠️
//    @Async 😱 n-a apucat publisherul sa dea COMMIT ca listenerul da SELECT (de craciun crapa)
    @Order(10)
    // SOC SI GROAZA: default toti listenerii ruleaza unul dupa altul
    // in threadul + tranzactia publisherului😱😱😱
    public void process(OrderPlacedEvent event) {
        log.info("Checking stock for products in order " + event.orderId());
        if (stock == 0) {
            throw new IllegalStateException("Out of stock");
        }
        stock--;
        log.info(">> PERSIST new STOCK!!");
    }
}
