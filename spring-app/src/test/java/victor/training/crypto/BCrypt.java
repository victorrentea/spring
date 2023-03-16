package victor.training.crypto;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BCrypt {
  @Test
  void explore() {
    int strength = 10; // bcrypt work factor - increase when CPU of machines improve
    BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder(strength, new SecureRandom());
    String hashedPassword = bcrypt.encode("actuator");

    System.out.println("{bcrypt}" + hashedPassword);

    // TODO assert that bcrypt#matches is true
    assertThat(bcrypt.matches("actuator", hashedPassword)).isTrue();
    // TODO assert that bcrypt#matches is false
    assertThat(bcrypt.matches("different", hashedPassword)).isFalse();
  }
}
