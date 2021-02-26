package victor.training.spring.web.controller.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
public class NotificationController {
   private final SimpMessagingTemplate simpMessagingTemplate;

   @GetMapping("notify")
   public void notifyAlLBrowsers() {
      log.info("notification sent ");
      simpMessagingTemplate.convertAndSend("/topic/notification", "Notificare");
   }

   // Alternativa este Long Polling https://ably.com/topic/long-polling?utm_campaign=JSK-105575&utm_content=JSK-105575&utm_medium=JSK-105575&utm_source=JSK-105575

   // websockets: Cum ii scrii unui singur browser ? din toate cele inregistrare
}
