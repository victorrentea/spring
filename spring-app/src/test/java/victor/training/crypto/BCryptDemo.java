package victor.training.crypto;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;

public class BCryptDemo {
  @Test
  void explore() {
    int strength = 10; // work factor of bcrypt
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(strength, new SecureRandom());
    String encodedPassword = encoder.encode("actuator");

    System.out.println(encodedPassword);


    System.out.println("matches = " + encoder.matches("actuator", encodedPassword));
  }
}
