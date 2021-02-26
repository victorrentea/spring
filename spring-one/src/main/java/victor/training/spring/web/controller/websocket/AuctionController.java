package victor.training.spring.web.controller.websocket;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.ScheduledFuture;

@Controller
@RequiredArgsConstructor
public class AuctionController {
   private final SimpMessagingTemplate simpMessagingTemplate;
   private final TaskScheduler taskScheduler;
   public static final String[] labels = {"SOLD!", "Twice", "Once"};

   // state, yes !
   private Bid bestBid;
   private ScheduledFuture<?> nextEndAnnounce;
   private int endTicks;

   @MessageMapping("/bid")
   @SendTo("/topic/auction-messages")
   public synchronized AuctionMessage greeting(Bid newBid) {
      if (bestBid != null && bestBid.getBid() >= newBid.getBid()) {
         return sendMessageToBrowser("Your bid is less then the current winner: " + bestBid);
      } else {
         bestBid = newBid;
         if (nextEndAnnounce != null) {
            nextEndAnnounce.cancel(false);
         }
         nextEndAnnounce = taskScheduler.schedule(this::endTick, getNextEndAnnounce());
         endTicks = 3;
         return sendMessageToBrowser(newBid.getBid() + " for " + newBid.getName() + "!");
      }
   }

   private AuctionMessage sendMessageToBrowser(String message) {
      DateTimeFormatter format = DateTimeFormatter.ofPattern("KK:mm:ss.SSS");
      String messageWithTimestamp = LocalDateTime.now().format(format) + " " + message;
      return new AuctionMessage(messageWithTimestamp);
   }

   private Date getNextEndAnnounce() {
      return DateUtils.addSeconds(new Date(), 2);
   }


   private synchronized void endTick() {
      endTicks--;
      AuctionMessage message = sendMessageToBrowser("Selling to " + bestBid.getName() + " for " + bestBid.getBid() + " ... " + labels[endTicks]);
      simpMessagingTemplate.convertAndSend("/topic/auction-messages", message);
      if (endTicks == 0) {
         bestBid = null;
      } else {
         nextEndAnnounce = taskScheduler.schedule(this::endTick, getNextEndAnnounce());
      }
   }

}
