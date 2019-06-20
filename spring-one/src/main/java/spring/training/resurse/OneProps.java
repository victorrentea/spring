package spring.training.resurse;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Data
public class OneProps {
    private String a;
    private B b;
    private List<String> list;
    private Map<String,String> map;

    @Data
    static class B {
        private String ba;
        private String bb;
    }
}
