package victor.training.spring.ms.bootservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TechnicalController {
   @Value("${hello.message}")
   private String helloMessage;

   @GetMapping("hello")
   public String hello() {
      return helloMessage;
   }

}
