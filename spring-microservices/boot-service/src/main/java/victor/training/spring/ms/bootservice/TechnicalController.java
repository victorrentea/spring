package victor.training.spring.ms.bootservice;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@Slf4j
@RefreshScope // recreaza instanta asta de bean . injectand si @postconstruct din nou
public class TechnicalController {
   @Value("${hello.message}")
   private String helloMessage;

   @PostConstruct
   public void checkMessageNotEmpty() {
      if (helloMessage.isEmpty()) {
         throw new IllegalArgumentException();
      }
   }


   @GetMapping("hello")
   public String hello() {
      log.debug("Stuff");
//      if (true) throw new IllegalArgumentException("inginer");
      return helloMessage;
   }
}

