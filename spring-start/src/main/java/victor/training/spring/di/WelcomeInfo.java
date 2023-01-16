package victor.training.spring.di;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Slf4j
@Data // getters & setters are mandatory!
@Component
@ConfigurationProperties(prefix = "welcome")
@Validated
public class WelcomeInfo {
//    @NotNull
    private String welcomeMessage = "Uau! la asa nu va asteptati";
    private List<URL> supportUrls;
    private Map<Locale, String> localContactPhone;
//    @Valid
    private HelpInfo help;

    @Data
    public static class HelpInfo {
        @NotNull
        private Integer appId;
        private File file;
    }

    @PostConstruct
    public void printMyselfAtStartup() {
        if (!help.file.isFile()) {
            throw new IllegalArgumentException("VALEU! n-am fisier: " + help.file);
        }
        // TODO validate help.file exists on disk
        // TODO validate welcome message is not null and at least 10 chars
        // TODO use javax.validation for the previous task. Hint: annotate class with @Validated
        log.info("My props: " + this);
    }
}
