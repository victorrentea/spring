package victor.training.spring.web.controller;

public class DOamneFereste {
  private static DOamneFereste INSTANCE;

  public static DOamneFereste getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new DOamneFereste();
    }
    return INSTANCE;
  }

  private DOamneFereste() {
  }
}
