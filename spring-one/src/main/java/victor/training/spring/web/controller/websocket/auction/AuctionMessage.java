package victor.training.spring.web.controller.websocket.auction;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuctionMessage {
  private final DateTimeFormatter format = DateTimeFormatter.ofPattern("KK:mm:ss.SSS");

  private String content;

  public AuctionMessage() {
  }

  public AuctionMessage(String content) {
    this.content = LocalDateTime.now().format(format) + " " + content;
  }

  public String getContent() {
    return content;
  }

}