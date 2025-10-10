package victor.training.spring.first.picking;

import org.springframework.stereotype.Service;

@Service
public class DronePicking implements PickingStrategy {

  @Override
  public void pick(String item) {

  }

  @Override
  public boolean appliesFor(String item) {
    return false;
  }
}
