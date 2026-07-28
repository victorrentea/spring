package victor.training.spring.first;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class Sloth {
  @PostConstruct
  public void atStartup() {
    System.out.println("Ma ridic de pe canapea");
  }

  public void utila() {
    System.out.println("Treaba");
  }

  public static void statica() {

  }
}
