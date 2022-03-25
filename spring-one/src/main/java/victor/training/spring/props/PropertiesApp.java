package victor.training.spring.props;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import javax.sql.DataSource;

@SpringBootApplication
public class PropertiesApp {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private MyClass myClass;

    @EventListener(ApplicationStartedEvent.class)
    public void method() {
        System.out.println("I have a ds " + dataSource );
        System.out.println("I myClass a ds " + myClass );
    }

    public static void main(String[] args) {
        SpringApplication.run(PropertiesApp.class);
    }

}

class MyClass {
    private final String a;

    MyClass(String a) {
        this.a = a;
    }

    @Override
    public String toString() {
        return "MyClass{" +
               "a='" + a + '\'' +
               '}';
    }
}

//@Order(2)
@Configuration
@ConditionalOnProperty(name = "myClass")
@ConditionalOnMissingBean(type = "victor.training.spring.props.MyClass")
class MyConfig {
    @Bean
    public MyClass myClass() {
        return new MyClass("a");
    }
}

//@Order(1)
@Configuration
@ConditionalOnProperty(name = "myClass")
@ConditionalOnMissingBean(type = "victor.training.spring.props.MyClass")
class MyConfig2 {
    @Bean
    public MyClass myClass() {
        return new MyClass("b");
    }
}
