package victor.training.spring.first.events.invoicing;

import org.springframework.context.ApplicationEvent;

public record StockReservedEvent(long orderId) {
}

