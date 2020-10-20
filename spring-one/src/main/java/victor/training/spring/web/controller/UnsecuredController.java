package victor.training.spring.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.props.WelcomeInfo;

@RequestMapping("unsecured")
@RestController
//@PreAuthorize("permitAll()")
public class UnsecuredController {
   @Autowired  // TODO Import the other Spring Boot Application
   private WelcomeInfo welcomeInfo;

   // TODO [SEC] allow unsecured access
   @GetMapping("welcome-info")
   public WelcomeInfo showWelcomeInfo(){
      return welcomeInfo;
   }

}
