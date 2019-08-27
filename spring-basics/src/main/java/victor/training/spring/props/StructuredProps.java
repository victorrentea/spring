package victor.training.spring.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Data
@Component
@ConfigurationProperties("structured")
public class StructuredProps {
    private String a;
    private B b;
    private List<String> list;
    private Map<String, String> map;

    @Data
    static class B {
        private String ba;
        private String bb;
    }
}
