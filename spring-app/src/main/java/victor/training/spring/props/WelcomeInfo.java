package victor.training.spring.props;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.File;
import java.net.URI;
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
    @NotNull
    @Size(min = 3)
    private String welcomeMessage = "default welcome";
    private List<URL> supportUrls;
    private Map<Locale, String> localContactPhone;
    private HelpInfo help;

    @Data
    public static class HelpInfo {
        private Integer appId;
        private File file;
    }

    @PostConstruct
    public void checkFIle() {
        if (!help.file.isFile()) {
            throw new IllegalArgumentException("BUM: not a file " + help.file);
        }
        System.out.println("Props: " +this);
    }







    @PostConstruct
    public void printMyselfAtStartup() {
        log.debug("My props: " + this);
    }
}
