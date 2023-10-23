package victor.training.spring.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

  private static final Logger log = LoggerFactory.getLogger(AdminController.class);

  @GetMapping("admin/launch")
  public String restart() {
    return "What does this red button do?     ... [Missile Launched]";
  }

  @GetMapping("admin/clear-cache")
  public String clearCaches() {
    return "Wipe out caches";
  }

  @GetMapping("api/salary/raise")
  public String raiseSalary(@RequestParam String who, @RequestParam String amount) {
    log.info("Raising salary of {} with {}", who, amount);
    return "Salary raised by admin!";
  }

}
