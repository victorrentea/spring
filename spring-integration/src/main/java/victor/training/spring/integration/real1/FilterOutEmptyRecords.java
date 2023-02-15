package victor.training.spring.integration.real1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FilterOutEmptyRecords {
  public boolean filter(InMessage message) {
    boolean result = !message.getTransfers().isEmpty();
    log.info("Filtered {} = {}", message, result);
    return result;
  }
}
