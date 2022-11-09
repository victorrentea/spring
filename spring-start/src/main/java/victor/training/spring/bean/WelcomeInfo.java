package victor.training.spring.bean;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Slf4j
@Data // getters & setters are mandatory! !!
@Component
@ConfigurationProperties(prefix = "welcome")
@Validated
public class WelcomeInfo {
    @NotNull
    @NotBlank
    private String welcomeMessage;
    private List<URL> supportUrls;
    private Map<Locale, String> localContactPhone;
    private HelpInfo help;

    @Data
    public static class HelpInfo {
        private Integer appId;
//        @CustomValidator // gandam style
        private File file;
    }

    @PostConstruct
    public void printMyselfAtStartup() {
//        if (!help.file.isFile()) {
//            throw new IllegalArgumentException("Nu e fisieru " + help.file);
//        }
        // TODO validate help.file exists on disk
        // TODO validate welcome message is not null and at least 10 chars
        // TODO use javax.validation for the previous task. Hint: annotate class with @Validated
        log.debug("My props: " + this);
    }
}
