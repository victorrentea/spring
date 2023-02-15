package victor.training.spring.integration.real1;

import org.springframework.stereotype.Component;

@Component
public class SomeTransformer {
  public String transform(String transfer) {
    return transfer.toUpperCase();
  }
}
