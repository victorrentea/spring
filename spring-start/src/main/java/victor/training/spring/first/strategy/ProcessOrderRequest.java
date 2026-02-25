package victor.training.spring.first.strategy;

public class ProcessOrderRequest {
  private String id;
  private String type;

  public ProcessOrderRequest() {
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}

