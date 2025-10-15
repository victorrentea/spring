package victor.training.spring.web;

import lombok.Data;

@Data
public class SendRequest {
  // AMQP exchange - empty string means default exchange
  private String exchange = "";
  // routing key (queue name for default exchange)
  private String routingKey;
  // raw XML payload to send
  private String xml;
}

