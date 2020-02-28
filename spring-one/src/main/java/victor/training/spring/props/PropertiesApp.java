package victor.training.spring.props;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class PropertiesApp {
    public static void main(String[] args) {
        SpringApplication.run(PropertiesApp.class);
    }

}

@Slf4j
@Data
@Component
class ClassWithTonsOfProperties {
    private String a;
    private List<String> list;
    private Map<String,String> map;
    // TODO B b;

    @PostConstruct
    public void printMyself() {
        log.debug("My props: " + this);
    }
}




