package victor.training.spring.web.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {


   // TODO [SEC] URL-pattern restriction: admin/**
   @GetMapping("launch")
   public String restart() {
      return "What does this red button do?     ... [Missile Launched]";
   }
   @GetMapping("poll-in-folder")
   public String poll() {
      return "What does this red button do?     ... [Missile Launched]";
   }

}
