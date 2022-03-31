package victor.training.spring.web.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@PreAuthorize("hasRole('ADMIN')")
//@RequestMapping("api/admin")
@RestController
public class AdminController {
   @GetMapping("api/admin/red")
   public String redButton() {
      return "Putin";
   }
}
