package victor.training.spring.bean;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Data // getters & setters are mandatory!
@Component
@ConfigurationProperties(prefix = "welcome")
public class WelcomeInfo {
    private String welcomeMessage;
    private List<URL> supportUrls;
    private Map<String,String> localContactPhone; // per country
    private List<ServiceDetails> webServiceEndpoints;
    private HelpInfo help;

    @Data
    public static class ServiceDetails {
        private URL url;
//        @NonNull
        private String user;
        private String pass;
        private Class<?> attProvider;
    }

    @Data
    public static class HelpInfo {
        private Integer appId;
        private File file;
    }

    @PostConstruct
    public void printMyself() {
//        for (ServiceDetails webServiceEndpoint : webServiceEndpoints) {
//            Objects.requireNonNull(webServiceEndpoint.getUser());
//        }
        // TODO validate: that help.file exists!
        System.out.println("My props: " + this);
    }
}
