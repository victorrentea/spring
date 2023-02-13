package victor.training.spring.bean;

import lombok.Data;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
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
@Data //get+set+tostring
@Component // makes spring see this
@ConfigurationProperties(prefix = "welcome") // spring to inject from yaml the props under welcome:
@Validated // calls for validation (spring to use reflection on the fields bellow and check javax.validation annotations0
public class WelcomeInfo {
    @NotNull // javax.validation
    private String welcomeMessage;
    @Size(min = 1)
    private List<URL> supportUrls;
    private Map<Locale, String> localContactPhone;
    private HelpInfo help;

    @Data
    public static class HelpInfo {
        Integer appId = -1; // default value.
        File file;
    }

    @PostConstruct
    public void printMyselfAtStartup() {
//        if (!help.file.isFile()) {
//            throw new IllegalArgumentException("NOT A FILE: " + help.file);
//        }
        // TODO validate help.file exists on disk
        // TODO validate welcome message is not null and at least 10 chars
        // TODO use javax.validation for the previous task. Hint: annotate class with @Validated
        log.info("My props: \n" + this);
    }
}

// this below allows for IMMUTABLE objects to be injected + @EnableConfigurationProperties on @SBA +
// lombok.anyConstructor.addConstructorProperties=true in lombok.config
//@Slf4j
//@Value
//@ConstructorBinding
//@ConfigurationProperties(prefix = "welcome")
//public class WelcomeInfo {
//    String welcomeMessage;
//    List<URL> supportUrls;
//    Map<Locale, String> localContactPhone;
//    HelpInfo help;
//
//    @Value
//    public static class HelpInfo {
//        Integer appId;
//        File file;
//    }
//
//    @PostConstruct
//    public void printMyselfAtStartup() {
//        // TODO validate help.file exists on disk
//        // TODO validate welcome message is not null and at least 10 chars
//        // TODO use javax.validation for the previous task. Hint: annotate class with @Validated
//        log.debug("My props: " + this);
//    }
//}
