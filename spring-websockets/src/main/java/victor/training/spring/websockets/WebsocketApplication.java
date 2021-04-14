package victor.training.spring.websockets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@SpringBootApplication
@EnableScheduling
@EnableWebSocketMessageBroker
public class WebsocketApplication implements WebSocketMessageBrokerConfigurer {
   public static void main(String[] args) {
      SpringApplication.run(WebsocketApplication.class, args);
   }


   @Override
   public void configureMessageBroker(MessageBrokerRegistry config) {
      config.enableSimpleBroker("/topic"); // browsers listen to /topic/...
      config.setApplicationDestinationPrefixes("/send"); // browsers send to /app/...
   }

   @Override
   public void registerStompEndpoints(StompEndpointRegistry registry) {
      registry.addEndpoint("/websocket").withSockJS();
   }


}
