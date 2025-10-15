package victor.training.spring;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import victor.training.spring.messages.Order;
import victor.training.spring.messages.Invoice;
import victor.training.spring.messages.Notification;
import victor.training.spring.messages.XmlMessage;

@Component
public class Rabbit {
  private static final Logger logger = LoggerFactory.getLogger(Rabbit.class);

  // The XmlMessageConverter registered in the listener container factory will
  // unmarshal XML -> POJO before this method is invoked, so accept XmlMessage.
  @RabbitListener(queues = "example.queue")
  public void receive(XmlMessage payload) {
    logger.info("Received payload of type: {}", payload == null ? null : payload.getClass().getName());
    try {
      if (payload instanceof Order order) {
        logger.info("Processed Order: {}", order);
        // ... process order
      } else if (payload instanceof Invoice invoice) {
        logger.info("Processed Invoice: {}", invoice);
        // ... process invoice
      } else if (payload instanceof Notification notification) {
        logger.info("Processed Notification: {}", notification);
        // ... process notification
      } else {
        logger.warn("Unhandled XmlMessage subtype: {}", payload == null ? null : payload.getClass());
      }
    } catch (Exception e) {
      logger.error("Failed to process payload", e);
    }
  }
}
