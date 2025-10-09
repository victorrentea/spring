package victor.training.spring.async;

public class HighSchool {
  private static HighSchool INSTANCE;

  public static HighSchool getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new HighSchool();
    }
    return INSTANCE;
  }

  private HighSchool() {
  }
}
