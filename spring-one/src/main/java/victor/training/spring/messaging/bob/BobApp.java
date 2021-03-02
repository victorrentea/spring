package victor.training.spring.messaging.bob;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import victor.training.spring.messaging.alice.AliceApp;

@Slf4j
@SpringBootApplication
public class BobApp implements CommandLineRunner {
   public static void main(String[] args) {
      new SpringApplicationBuilder(BobApp.class)
          .profiles("spa")
          .properties("server.port:8081")
          .run(args);
   }

   @Override
   public void run(String... args) throws Exception {
      log.debug("Started!");
   }
}

@Slf4j
@MessageEndpoint
class Listener {
   @ServiceActivator(inputChannel = "req_q_in")
   public void method(String message) {
      log.info("Got message: " + message);
   }
}


interface BobChannels {
   String req_q_in = "req_q_in";

   @Input(req_q_in)
   SubscribableChannel req_q_in();
}
