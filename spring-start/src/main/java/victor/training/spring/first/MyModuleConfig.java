package victor.training.spring.first;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@EnableConfigurationProperties(Props.class)
// fill the fields of this class with the values from application.properties/.yml
// and make it available for injection (add it as a bean in the context)

// by default spring loads application.properties/.yml
// from /src/main/resources or /src/main/resources/config
@Import({
    X.class,
    Y2WithProps.class,
    X2.class,
    YMe.class,
    Y2.class,
    ValidatingRequestPayload.class,
    MailServiceImpl.class,
    MailServiceDummy.class,
//    ClassFromAJar.class,// wont work because the class needs manual initialization
})
@Configuration // when this appears on a class
// then when a @Bean method calls another @Bean method
// they don't really call each other.Spring tricks you!
// when you call #theBeanName(null) from #externalClass
// spring INTERCEPTS your call and fills the argument

// these calls act as "bean references"
public class MyModuleConfig {
  @Profile("local")
  @Bean // allows to create a bean in the Spring context programatically
//  public ClassFromAJar theBeanName(@Value("${props.gate}") int gate) {
  public ClassFromAJar theBeanName(Props props) {
    System.out.println("I am called now with props: " + props);
    ClassFromAJar bean = new ClassFromAJar(props.welcomeMessage());
    bean.setState(props.gate()); // < props.gate
    return bean;
  }
//  @Bean use this!!
//  public ExternalClass externalClass(ClassFromAJar dep) {
//    return new ExternalClass(dep);
//  }
  @Bean
  public ExternalClass externalClass() {
    return new ExternalClass(theBeanName(null));
  }
  @Bean
  public ExternalClass externalClass1() {
    return new ExternalClass(theBeanName(null));
  }
  @Bean
  public ExternalClass externalClass2() {
    return new ExternalClass(theBeanName(null));
  }
}
class ExternalClass {
  private final ClassFromAJar dep;

  ExternalClass(ClassFromAJar dep) {
    this.dep = dep;
    System.out.println("Got a class from a jar with state: " + dep.getName());
  }
}
//@Configuration // without

