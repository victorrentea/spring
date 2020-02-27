package spring.training.props;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "structured")
public class ClassUsingTonsOfProperties {
    private String a;
    private List<String> list;
    private Map<String,String> map;

    private B b;

    @PostConstruct
    public void show() {
        System.out.println("Props: " + this);
    }
}
@Data
class B {
    private String ba;
    private String bb;
}