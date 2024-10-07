package victor.training.spring.first;

// 🔒 you can't change this class as it's from a JAR. not yours!!@!!#&($RT!&*$!^
public class ClassFromAJar {
  private final String name;
  private Integer state;

  public ClassFromAJar(String otherState) {
    System.out.println("NEW INSTANCE OF " + getClass().getSimpleName());
    System.out.println("otherState = " + otherState);
    this.name = otherState;
  }

  public String getName() {
    return name;
  }

  public void setState(Integer state) {
    this.state = state;
  }

  public Integer getState() {
    return state;
  }
}
