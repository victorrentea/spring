package spring.training.props;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;


@Component
@ToString
public class ClassWithLoadsOfProps {
    @Autowired
    StructuredConfig config;

    @PostConstruct
    public void printAll() {
        System.out.println(config);
    }

}

@Data
@Configuration
@ConfigurationProperties(prefix = "structured")
class StructuredConfig {
    private String a;
    private B b;
    private List<String> list;
    private Map<String,String> map;

    @Data
    static class B {
        private String bb;
        private String ba;
    }
}


