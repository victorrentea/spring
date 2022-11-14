package victor.training.spring.properties;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Slf4j
@Data // getters & setters are mandatory!
@Component
@ConfigurationProperties("welcome")
@Validated // tells sprign to check all private fields for javax.validation annotations
public class WelcomeInfo {
    @NotNull
    @Size(min = 1)
    private String welcomeMessage = "Hi!";
    private List<URL> supportUrls;
    private Map<Locale, String> localContactPhone;
    private HelpInfo help;

    @Data
    public static class HelpInfo {
        private Integer appId;
        private File file;
    }

    @PostConstruct
    public void printMyselfAtStartup() {
        if (!help.file.isFile()) {
            throw new IllegalArgumentException("BUM not a file: " + help.file);
        }
        // TODO validate help.file exists on disk
        // TODO validate welcome message is not null and at least 10 chars
        // TODO use javax.validation for the previous task. Hint: annotate class with @Validated
        log.info("My props: " + this);
    }
}
