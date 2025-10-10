package victor.training.spring.first.picking;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PickerService {
  private final List<PickingStrategy> allStrategies;

  public PickerService(List<PickingStrategy> allStrategies) {
    this.allStrategies = allStrategies;
  }

  @PostConstruct
  public void method() {
    String item = "tomatoes";
    for (PickingStrategy strategy : allStrategies) {
      if (strategy.appliesFor(item)) {
        strategy.pick(item);
      }
    }
  }
}
