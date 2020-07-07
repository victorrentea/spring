package victor.training.spring.props;

import lombok.Data;
import lombok.ToString;
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
@ConfigurationProperties(prefix = "welcome")
public class WelcomeInfo {
    private String welcomeMessage;
    private List<String> supportUrls;
    private Map<String,String> localContactPhone; // per country
    // TODO HelpInfo help;

    @PostConstruct
    public void printMyself() {
        log.debug("My props: " + this);
    }
}
