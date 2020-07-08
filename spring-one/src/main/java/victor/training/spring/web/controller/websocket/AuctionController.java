package victor.training.spring.web.controller.websocket;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.concurrent.ScheduledFuture;

@Controller
@RequiredArgsConstructor
public class AuctionController {
   private final SimpMessagingTemplate simpMessagingTemplate;
   private final TaskScheduler scheduler;
   public static final String[] labels = {"SOLD!", "Twice", "Once"};

   // state, yes !
   private Bid bestBid;
   private ScheduledFuture<?> nextEndAnnounce;
   private int endTicks;

   @MessageMapping("/place-bid")
   @SendTo("/topic/auction-messages")
   public synchronized AuctionMessage greeting(Bid newBid) {
      if (bestBid != null && bestBid.getBid() >= newBid.getBid()) {
         return new AuctionMessage("Your bid is less then the current winner: " + bestBid);
      } else {
         bestBid = newBid;
         if (nextEndAnnounce != null) {
            nextEndAnnounce.cancel(false);
         }
         nextEndAnnounce = scheduler.schedule(this::endTick, getNextEndAnnounce());
         endTicks = 3;
         return new AuctionMessage(newBid.getBid() + " for " + newBid.getName() + "!");
      }
   }

   private Date getNextEndAnnounce() {
      return DateUtils.addSeconds(new Date(), 2);
   }


   private synchronized void endTick() {
      endTicks--;
      AuctionMessage message = new AuctionMessage("Selling to " + bestBid.getName() + " for " + bestBid.getBid() + " ... " + labels[endTicks]);
      simpMessagingTemplate.convertAndSend("/topic/auction-messages", message);
      if (endTicks == 0) {
         bestBid = null;
      } else {
         nextEndAnnounce = scheduler.schedule(this::endTick, getNextEndAnnounce());
      }
   }

   // app practice:
   // - userul uploadeaza un fisier mare si vrea sa vada popup "Done" cand e gata.
   // - ai vreun UI sexy care e actualizat realtime: GIS map in HTML5. -- ca sa eviti sute de browsere chemandu-te peste HTTP la fiecare secunda. ("polling")
   // - live chat (latente zero)

}
