package victor.training.spring.props;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Map;

@Slf4j
@Data // getters & setters are mandatory!
@Component
@Validated
@ConfigurationProperties(prefix = "welcome")
public class WelcomeInfo {
    @NotNull
    @Size(min = 5)
    private String welcomeMessage = "Hello world !";
    private List<URL> supportUrls;
    private Map<String, String> localContactPhone; // per country
    private HelpInfo help;

    @Data
    public static class HelpInfo {
        private Integer appId;
        private File file;
    }


    @Autowired
    Validator validator;

    @PostConstruct
    public void printMyself() {
        // TODO validate help.file exists on disk
        // TODO validate welcome message is not null
        // TODO validate welcome message is at least 10 chars
        // TODO use javax.validation for the previous 2 tasks. Tip: annotate class with @Validated
        log.debug("My props: " + this);
    }


}
