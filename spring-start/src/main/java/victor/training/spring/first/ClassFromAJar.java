package victor.training.spring.first;

// 🔒 you can't change this class as it's from a JAR. not yours!!@!!#&($RT!&*$!^
public class ClassFromAJar {
  private Integer state;

  public ClassFromAJar(String otherState) {
    System.out.println("NEW INSTANCE OF " + getClass().getSimpleName());
    System.out.println("otherState = " + otherState);
  }

  public void setState(Integer state) {
    this.state = state;
  }

  public Integer getState() {
    return state;
  }
}
