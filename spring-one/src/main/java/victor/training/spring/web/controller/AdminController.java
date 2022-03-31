package victor.training.spring.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("api/admin")
@RestController
public class AdminController {
   @GetMapping("red")
   public String redButton() {
      return "Putin";
   }
}
