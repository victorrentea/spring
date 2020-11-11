package victor.training.spring.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("unsecured")
public class UnsecuredController {
   // TODO [SEC] allow unsecured access
   @GetMapping("welcome-info")
   public String showWelcomeInfo(){
      return "Welcome. What's your temperature today?";
   }

   @GetMapping("bye/info")
   public String showByeMessage(){
      return "Take care unde arunci masca.";
   }
}
