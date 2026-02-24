package victor.training.spring.first;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Import({
    X.class, // defines exactly ONE instance of X (singleton) named "x"
    victor.training.spring.first.subp.X.class,
    Y.class,
    Z.class,
    MailServiceDummy.class,
    MailServiceImpl.class
})
@EnableConfigurationProperties(Props.class)
public class FirstConfig {

  @Bean // defines a bean named "t" of type T
  public T t() {
    return new T();
  }

  @Bean // defines a bean named "t2" of type T
  public T t2() {
    return new T();
  }


}

//class Singl {
//  private static Singl INSTANCE;
//
//  public static Singl getInstance() {
//    if (INSTANCE == null) {
//      INSTANCE = new Singl();
//    }
//    return INSTANCE;
//  }
//
//  private Singl() {
//  }
//}
