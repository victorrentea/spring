package victor.training.spring.first.strategy;

public class GroceryOrder {
  private final String id;
  private final String type; // e.g. ONLINE, INSTORE, INTERNATIONAL

  public GroceryOrder(String id, String type) {
    this.id = id;
    this.type = type;
  }

  public String getId() {
    return id;
  }

  public String getType() {
    return type;
  }

  @Override
  public String toString() {
    return "Order{id='" + id + "', type='" + type + "'}";
  }
}

