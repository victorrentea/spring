package victor.training.spring.web.controller.websocket;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuctionMessage {
  private String content;

  public AuctionMessage() {
  }

  public AuctionMessage(String content) {
    this.content = content;
  }

  public String getContent() {
    return content;
  }

}