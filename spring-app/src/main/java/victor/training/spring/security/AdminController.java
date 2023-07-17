package victor.training.spring.security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

  // TODO URL-pattern restriction: admin/**
  @GetMapping("admin/launch")
  public String restart() {
    return "What does this red button do?     ... [Missile Launched]";
  }

  @GetMapping("admin/clear-cache")
  public String clearCaches() {
    return "Wipe out caches";
  }

}
