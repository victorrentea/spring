package victor.training.spring.web.controller.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Bid {
  private String name;
  private int bid;
}