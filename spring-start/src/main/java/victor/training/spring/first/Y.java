package victor.training.spring.first;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import victor.training.spring.first.pack.X;

@Service
@RequiredArgsConstructor
public class Y {
  @Value("${welcome.welcomeMessage}") // inject this from the configuration files
  private final String message;

  @Lazy
  private final X x;

//  public Y(String message, X x) {
//    this.message = message;
//    this.x = x;
//  }

//  public Y(@Lazy X x) {
//    this.x = x;
//  }

  // (recommended) constructor injection => 😏 replace with @RequiredArgsConstructor
}
