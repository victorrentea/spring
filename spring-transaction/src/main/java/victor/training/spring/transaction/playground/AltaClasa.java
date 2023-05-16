package victor.training.spring.transaction.playground;

import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
public class AltaClasa {
  @EventListener
  @Order(1)
  public void method(CleanupAfterTransactionEvent event) {

  }
  @EventListener
  @Order(2)
  public void doi(CleanupAfterTransactionEvent event) {

  }
  @EventListener
  @Order(4)
  public void tri(CleanupAfterTransactionEvent event) {

  }
  @EventListener
  public void sieu(CleanupAfterTransactionEvent event) {

  }
}
