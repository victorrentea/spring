package victor.training.spring.first;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import victor.training.spring.first.pack.X;

@Service
public class Y {
//  @Value("${welcome.welcomeMessage}") // inject this from the configuration files
  private final String message = "HALO";

  @Autowired
  private X x;

  // (recommended) constructor injection => 😏 replace with @RequiredArgsConstructor
}
