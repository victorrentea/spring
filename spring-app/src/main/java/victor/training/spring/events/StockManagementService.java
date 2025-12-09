package victor.training.spring.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StockManagementService {
    private int stock = 3; // silly implem :D

    public void process(long orderId) {
        log.info("Checking stock for products in order " + orderId);
        if (stock == 0) {
            throw new IllegalStateException("Out of stock");
        }
        stock--;
        log.info(">> PERSIST new STOCK!!");
    }
}
