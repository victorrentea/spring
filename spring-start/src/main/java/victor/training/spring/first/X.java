package victor.training.spring.first;

//@Controller // is for server-side generated HTML: .jsp, .jsf, vaadin, velocity
//@RestController // REST API calls


//@Repository // DB access

//all above are
// @Component // when none of te above applies < marks it for @ComponentScan that Picnic doesn't use.

//@Service // business logic 🧠🧠
public class X {
//  @Autowired // not in /src/main
  private final Y y;
  private final Z z;

  public X(Y y, Z z) {
    this.y = y;
    this.z = z;
  }

  public int logic() {
    return 1 + y.logic();
  }
}
