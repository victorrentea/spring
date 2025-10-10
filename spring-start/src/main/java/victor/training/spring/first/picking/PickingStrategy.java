package victor.training.spring.first.picking;

// Chain of Reponsibility Design Pattern (GoF)
public interface PickingStrategy {
  void pick(String item);
  boolean appliesFor(String item);
}
