package victor.training.crypto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;

public class BCrypt {
  // bcrypt work factor - increase this when average CPU power of machines improve
  public static final int STRENGTH = 10;

  @Test
  void explore() {
    BCryptPasswordEncoder bcryptEncoder = new BCryptPasswordEncoder(STRENGTH, new SecureRandom());
    String encodedPassword = bcryptEncoder.encode("parola");

    System.out.println("{bcrypt}" + encodedPassword);

    // TODO assert that bcrypt#matches is true
    BCryptPasswordEncoder bcryptMatcher = new BCryptPasswordEncoder(STRENGTH, new SecureRandom());
    Assertions.assertThat(bcryptMatcher.matches("parola", encodedPassword)).isTrue();

    // TODO assert that bcrypt#matches is false
    Assertions.assertThat(bcryptMatcher.matches("different", encodedPassword)).isFalse();

  }
}
