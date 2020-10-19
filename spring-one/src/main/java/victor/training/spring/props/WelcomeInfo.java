package victor.training.spring.props;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "welcome")
public class WelcomeInfo {
    private static final Logger log = LoggerFactory.getLogger(WelcomeInfo.class);

    private String welcomeMessage;
    private List<String> supportUrls;
    private Map<String,String> localContactPhone; // per country
    private HelpInfo help;

    public static class HelpInfo {
        private URL helpUrl;
        private URI iconUri;

        public URL getHelpUrl() {
            return helpUrl;
        }

        public HelpInfo setHelpUrl(URL helpUrl) {
            this.helpUrl = helpUrl;
            return this;
        }

        public URI getIconUri() {
            return iconUri;
        }

        public HelpInfo setIconUri(URI iconUri) {
            this.iconUri = iconUri;
            return this;
        }

        @Override
        public String toString() {
            return "HelpInfo{" +
                   "helpUrl=" + helpUrl +
                   ", iconUri=" + iconUri +
                   '}';
        }
    }

    @PostConstruct
    public void printMyself() {
        log.debug("My props: " + this);
    }

    @Override
    public String toString() {
        return "WelcomeInfo{" +
               "welcomeMessage='" + welcomeMessage + '\'' +
               ", supportUrls=" + supportUrls +
               ", localContactPhone=" + localContactPhone +
               ", help=" + help +
               '}';
    }

    public String getWelcomeMessage() {
        return welcomeMessage;
    }

    public WelcomeInfo setWelcomeMessage(String welcomeMessage) {
        this.welcomeMessage = welcomeMessage;
        return this;
    }

    public List<String> getSupportUrls() {
        return supportUrls;
    }

    public WelcomeInfo setSupportUrls(List<String> supportUrls) {
        this.supportUrls = supportUrls;
        return this;
    }

    public Map<String, String> getLocalContactPhone() {
        return localContactPhone;
    }

    public WelcomeInfo setLocalContactPhone(Map<String, String> localContactPhone) {
        this.localContactPhone = localContactPhone;
        return this;
    }

    public HelpInfo getHelp() {
        return help;
    }

    public WelcomeInfo setHelp(HelpInfo help) {
        this.help = help;
        return this;
    }

    public static Logger getLog() {
        return log;
    }
}
