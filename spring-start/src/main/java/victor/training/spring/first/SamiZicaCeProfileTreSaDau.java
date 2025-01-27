package victor.training.spring.first;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class SamiZicaCeProfileTreSaDau {
  @Autowired
  ApplicationContext applicationContext;
  @PostConstruct
  public void laStartup() {
    // obtine lista de profile active
    String[] activeProfiles = applicationContext.getEnvironment().getActiveProfiles();
    // tipa daca nu e nici "prod" nici "dev". si spune-i si ce sa puna in command line
    if (activeProfiles.length != 1 ||
        !activeProfiles[0].equals("prod") &&
        !activeProfiles[0].equals("dev")) {
      throw new IllegalStateException("Trebuie sa dai unul dintre profilele 'prod' sau 'dev' ca argument la java -Dspring.profiles.active=prod");
    }

  }
}
