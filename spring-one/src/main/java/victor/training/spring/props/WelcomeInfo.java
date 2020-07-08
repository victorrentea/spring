package victor.training.spring.props;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Map;

@Slf4j
@Data // getters & setters mandatory!
@Component
public class WelcomeInfo {
    private String welcomeMessage;
    private List<String> supportUrls;
    private Map<String,String> localContactPhone; // per country
    private HelpInfo help;

    @Component
    public static class HelpInfo {
        private URL helpUrl;
        private URI iconUri;
    }

    @PostConstruct
    public void printMyself() {
        log.debug("My props: " + this);
    }
}
