package victor.training.spring.messaging.alice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import victor.training.spring.web.SpaApplication;

@Slf4j
@EnableBinding(AliceChannels.class)
@SpringBootApplication
public class AliceApp implements CommandLineRunner {
   public static void main(String[] args) {
      new SpringApplicationBuilder(AliceApp.class)
          .profiles("spa")
          .run(args);
   }

   @Autowired
   private AliceChannels channels;

   @Override
   public void run(String... args) throws Exception {
      log.debug("Trimit mesaj... ");
      channels.req_q_out().send(MessageBuilder.withPayload("Alice").build());
      log.debug("Am trimis");
   }
}

interface AliceChannels {
   String req_q_out = "req_q_out";

   @Output(req_q_out)
   MessageChannel req_q_out();
}
