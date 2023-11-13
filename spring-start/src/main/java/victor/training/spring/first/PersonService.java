package victor.training.spring.first;

// 1: n-am voie sa pun ca e in jar:
// 2: vreau sa o configurez pana o pun in context
//    sau clasa respectiva nu are default constructor
//@Component
public class PersonService {
  private String path;

  public PersonService(String path) {
    this.path = path;
  }

  public void init() {
    System.out.println("INIT!");
  }

  public void method() {
    System.out.println("Path = " + path);
  }
}
