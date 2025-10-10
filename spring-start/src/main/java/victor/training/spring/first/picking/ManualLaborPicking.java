package victor.training.spring.first.picking;

import org.springframework.stereotype.Service;

@Service
public class ManualLaborPicking implements PickingStrategy {

  @Override
  public void pick(String item) {
    System.out.println("Runners picking tomatoes from the field on the way to you");
  }

  @Override
  public boolean appliesFor(String item) {
    return item.equals("tomatoes");
  }
}
