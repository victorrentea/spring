package victor.training.spring.props;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Slf4j
@Data
@Component
public class ClassUsingTonsOfProperties {
    private String a;
    private List<String> list;
    private Map<String,String> map;
    // TODO B b;

    @PostConstruct
    public void printMyself() {
        log.debug("My props: " + this);
    }
}
