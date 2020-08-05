package victor.training.spring.ms.bootservice;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RefreshScope
@RestController
public class TechnicalController {
   @Value("${hello.message}")
   private String helloMessage;

   @PostConstruct
   public void checkMessageIsNotBlank() {
      if (Strings.isBlank(helloMessage)) {
         throw new IllegalArgumentException();
      }
   }

   @GetMapping("hello")
   public String hello() {
      return helloMessage;
   }
   @GetMapping("bye")
   public String bye() {
      return singleton.get();
   }

   @Autowired
   Singleton singleton;
}



@Service
class Singleton {
   private final RS rs;

   Singleton(RS rs) {
      System.out.println("Face si asta new ? : "+ rs.getClass());
      this.rs = rs;
   }

   public String get() {
      return rs.getHelloMessage();
   }
}

@RefreshScope
@Service
class RS {
   public RS() {
      System.out.println("new RS");
   }
   @Value("${hello.message}")
   private String helloMessage;

   @PostConstruct
   public void m() {
      System.out.println("PostConstruct");
   }

   public String getHelloMessage() {
      return helloMessage;
   }
}