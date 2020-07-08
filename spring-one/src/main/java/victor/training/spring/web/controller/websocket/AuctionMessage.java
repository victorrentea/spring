package victor.training.spring.web.controller.websocket;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuctionMessage {
  private final DateTimeFormatter format = DateTimeFormatter.ofPattern("KK:mm:ss.SSS");

  private String contentX;

  public AuctionMessage() {
  }

  public AuctionMessage(String contentX) {
    this.contentX = LocalDateTime.now().format(format) + " " + contentX;
  }

  public String getContentX() {
    return contentX;
  }

}