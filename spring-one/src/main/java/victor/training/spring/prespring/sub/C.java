package victor.training.spring.prespring.sub;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class C {
   @GetMapping("/api/call1")
   public void method() {

   }
}
