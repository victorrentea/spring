package victor.training.spring.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Secured("ROLE_ADMIN")
// you can create an aspect that checks that you went throught the authorization
public class AdminController {

  // TODO URL-pattern restriction: admin/**
  @GetMapping("admin/launch")
  public String restart() {
    log.info("[TAG-12516] App was restarted"); // DO NOT REMOVE THAT TOKEN!! because kibana scraps the logs
    // for this token and raises alerts,
    return "What does this red button do?     ... [Missile Launched]";
  }

  @GetMapping("admin/clear-cache")
  public String clearCaches() {
    return "Wipe out caches";
  }

}
