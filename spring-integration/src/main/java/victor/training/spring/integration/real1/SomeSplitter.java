package victor.training.spring.integration.real1;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SomeSplitter {
  public List<String> split(InMessage message) {
    return message.getTransfers();
  }
}
