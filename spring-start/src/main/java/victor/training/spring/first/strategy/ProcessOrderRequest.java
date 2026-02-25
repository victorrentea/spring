package victor.training.spring.first.strategy;

public class ProcessOrderRequest {
  private String id;
  private OrderType type;

  public ProcessOrderRequest() {
  }

  public String getId() {
    return id;
  }

  public ProcessOrderRequest setId(String id) {
    this.id = id;
    return this;
  }

  public OrderType getType() {
    return type;
  }

  public ProcessOrderRequest setType(OrderType type) {
    this.type = type;
    return this;
  }
}
