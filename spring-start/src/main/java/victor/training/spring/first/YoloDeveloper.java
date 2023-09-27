package victor.training.spring.first;


import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

// You Only Live Once = baga-ti picioru'
@Component
@Profile("prod") // risk = logica asta va rula DOAR IN PROD 💣
// "I don't always test my code, but when I do, I do it in production"
// - din ideologia unui YOLO DEVELOPER
public class YoloDeveloper {
  public void method() {
    // logica grea
  }
}
