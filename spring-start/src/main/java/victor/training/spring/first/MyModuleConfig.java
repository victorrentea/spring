package victor.training.spring.first;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

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
public class MyModuleConfig {
  @Bean // allows to create a bean in the Spring context programatically
  public ClassFromAJar theBeanName() {
    ClassFromAJar bean = new ClassFromAJar("init");
    bean.setState(1);
    return bean;
  }
}

//@Configuration // without

