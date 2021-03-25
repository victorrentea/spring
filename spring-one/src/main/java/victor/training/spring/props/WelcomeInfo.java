package victor.training.spring.props;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Map;

@Component
public class WelcomeInfo {
    private static final Logger log = LoggerFactory.getLogger(WelcomeInfo.class);
    private String welcomeMessage;
    private List<URL> supportUrls;
    private Map<String,String> localContactPhone; // per country
    private HelpInfo help;

    @Component
    public static class HelpInfo {
        private Integer appId;
        private File file;

        public Integer getAppId() {
            return appId;
        }

        public void setAppId(Integer appId) {
            this.appId = appId;
        }

        public File getFile() {
            return file;
        }

        public void setFile(File file) {
            this.file = file;
        }

        @Override
        public String toString() {
            return String.format("HelpInfo{appId=%d, file=%s}", appId, file);
        }
    }

    @PostConstruct
    public void printMyself() {
        log.debug("My props: " + this);
    }

    public String toString() {
        return String.format("WelcomeInfo{welcomeMessage='%s', supportUrls=%s, localContactPhone=%s, help=%s}", welcomeMessage, supportUrls, localContactPhone, help);
    }

    public static Logger getLog() {
        return log;
    }

    public String getWelcomeMessage() {
        return welcomeMessage;
    }

    public void setWelcomeMessage(String welcomeMessage) {
        this.welcomeMessage = welcomeMessage;
    }

    public List<URL> getSupportUrls() {
        return supportUrls;
    }

    public void setSupportUrls(List<URL> supportUrls) {
        this.supportUrls = supportUrls;
    }

    public Map<String, String> getLocalContactPhone() {
        return localContactPhone;
    }

    public void setLocalContactPhone(Map<String, String> localContactPhone) {
        this.localContactPhone = localContactPhone;
    }

    public HelpInfo getHelp() {
        return help;
    }

    public void setHelp(HelpInfo help) {
        this.help = help;
    }
}
