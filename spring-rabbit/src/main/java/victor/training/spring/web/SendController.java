package victor.training.spring.web;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class SendController {

  private final RabbitTemplate rabbitTemplate;

  public SendController(RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;
  }

  @PostMapping("/send")
  public ResponseEntity<?> send(@RequestBody SendRequest request) {
    if (request == null || request.getRoutingKey() == null || request.getRoutingKey().isBlank()) {
      Map<String, Object> body = new HashMap<>();
      body.put("error", "routingKey is required");
      return ResponseEntity.badRequest().body(body);
    }

    String xml = request.getXml() == null ? "" : request.getXml();
    byte[] payload = xml.getBytes(StandardCharsets.UTF_8);

    MessageProperties props = new MessageProperties();
    props.setContentType("text/xml");
    props.setContentEncoding(StandardCharsets.UTF_8.name());
    props.setContentLength(payload.length);

    Message message = new Message(payload, props);

    rabbitTemplate.send(request.getExchange(), request.getRoutingKey(), message);

    Map<String, Object> resp = new HashMap<>();
    resp.put("status", "sent");
    resp.put("exchange", request.getExchange());
    resp.put("routingKey", request.getRoutingKey());
    resp.put("bytes", payload.length);
    return ResponseEntity.accepted().body(resp);
  }

  @GetMapping("/send")
  public ResponseEntity<?> sendDefault() {
    // Default: send a sample Order XML to the example.queue using the default exchange
    String xml = "<Order>" +
        "<Id>ORD-DEFAULT</Id>" +
        "<Customer>BrowserUser</Customer>" +
        "<Items>" +
        "  <Item><SKU>SAMPLE</SKU><Quantity>1</Quantity><Price>9.99</Price></Item>" +
        "</Items>" +
        "<Total>9.99</Total>" +
        "</Order>";

    byte[] payload = xml.getBytes(StandardCharsets.UTF_8);
    MessageProperties props = new MessageProperties();
    props.setContentType("text/xml");
    props.setContentEncoding(StandardCharsets.UTF_8.name());
    props.setContentLength(payload.length);

    Message message = new Message(payload, props);
    String exchange = ""; // default exchange
    String routingKey = "example.queue";

    rabbitTemplate.send(exchange, routingKey, message);

    Map<String, Object> resp = new HashMap<>();
    resp.put("status", "sent");
    resp.put("exchange", exchange);
    resp.put("routingKey", routingKey);
    resp.put("bytes", payload.length);
    resp.put("note", "Default sample Order XML sent. Use POST /api/send to send custom XML.");
    return ResponseEntity.accepted().body(resp);
  }
}
