package victor.training.spring.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/admin")
@RestController
public class AdminController {
   @GetMapping("launch")
   public String restart() {
      // TODO [SEC] URL-pattern restriction: admin/**
      return "What does this red button do?     ... [Missile Launched]";
   }

}
