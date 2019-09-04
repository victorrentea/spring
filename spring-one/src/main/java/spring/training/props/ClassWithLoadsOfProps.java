package spring.training.props;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;


@Component
@ToString
public class ClassWithLoadsOfProps {
    @Value("${structured.a}")
    private String a;
    @Value("${structured.list}")
    private List<String> list;
    @Value("#{${structured.map}}")
    private Map<String,String> map;

    @PostConstruct
    public void printAll() {
        System.out.println(this);
    }

}
