package victor.training.spring.bean;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Data // getters & setters are mandatory!
@Component
@ConfigurationProperties(prefix = "welcome")
@Validated
public class WelcomeInfo {
    @NotNull
    @Size(min = 4)
    private String welcomeMessage = "Buna!";
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
//        Objects.requireNonNull(welcomeMessage, "");
//        if (!help.file.isDirectory()) {
//            throw new IllegalArgumentException("Valeu: " + help.file);
//        }
        log.info("My props: " + this);
    }
}
