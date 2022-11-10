package victor.training.spring.events.events;

public final class OrderInStockEvent {
    private final long orderId;

    public OrderInStockEvent(long orderId) {
        this.orderId = orderId;
    }

    public long getOrderId() {
        return this.orderId;
    }

    public String toString() {
        return "OrderInStockEvent(orderId=" + this.getOrderId() + ")";
    }
}
