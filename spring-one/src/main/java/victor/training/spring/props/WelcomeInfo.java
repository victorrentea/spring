package victor.training.spring.props;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Map;

interface TaxCalculator {
    int computeTax(int value);
}

@Component
@Profile("NL")
class EUTaxCalculator implements TaxCalculator {
    public int computeTax(int value) {
        return 1;
    }
}
@Component
@Profile("UK")
class UKTaxCalculator implements TaxCalculator {
    public int computeTax(int value) {
        return 2;
    }
}
@Component
class RunsAtStartup /*implements CommandLineRunner*/ { //1
    @Autowired
    private TaxCalculator taxCalculator;

//    @Override

//    public void run(String... args) throws Exception {
//    @EventListener(ApplicationStartedEvent.class) // 2
    @PostConstruct
    public void method() {
        System.out.println("Now using the following tax Calculator : " + taxCalculator);
    }
}

// where can we use @Value("${welcome.welcome-message}")

// getters & setters are mandatory!
@Component
@ConfigurationProperties(prefix = "welcome")
public class WelcomeInfo {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(WelcomeInfo.class);


    @PostConstruct
    public void checkHelpFileExists() {
        if (!help.file.isFile()) {
//            throw new IllegalArgumentException("File does not exist");
        }
    }
    private String welcomeMessage;
    private Map<String,String> localContactPhone; // per country

    public Map<String, Class<?>> getCountryToTaxCalculator() {
        return countryToTaxCalculator;
    }

    public WelcomeInfo setCountryToTaxCalculator(Map<String, Class<?>> countryToTaxCalculator) {
        this.countryToTaxCalculator = countryToTaxCalculator;
        return this;
    }

    private Map<String,Class<?>> countryToTaxCalculator; // per country
    private List<URL> supportUrls;
    private HelpInfo help;

    @Override
    public String toString() {
        return "WelcomeInfo{" +
               "welcomeMessage='" + welcomeMessage + '\'' +
               ", localContactPhone=" + localContactPhone +
               ", countryToTaxCalculator=" + countryToTaxCalculator +
               ", supportUrls=" + supportUrls +
               ", help=" + help +
               '}';
    }

    public static class HelpInfo {
        private Integer appId;
        private File file;

        public HelpInfo() {
        }

        public Integer getAppId() {
            return this.appId;
        }

        public File getFile() {
            return this.file;
        }

        public void setAppId(Integer appId) {
            this.appId = appId;
        }

        public void setFile(File file) {
            this.file = file;
        }

        @Override
        public String toString() {
            return "HelpInfo{" +
                   "appId=" + appId +
                   ", file=" + file +
                   '}';
        }
    }
    public String getWelcomeMessage() {
        return this.welcomeMessage;
    }

    public Map<String, String> getLocalContactPhone() {
        return this.localContactPhone;
    }

    public List<URL> getSupportUrls() {
        return this.supportUrls;
    }

    public HelpInfo getHelp() {
        return this.help;
    }

    public void setWelcomeMessage(String welcomeMessage) {
        this.welcomeMessage = welcomeMessage;
    }

    public void setLocalContactPhone(Map<String, String> localContactPhone) {
        this.localContactPhone = localContactPhone;
    }

    public void setSupportUrls(List<URL> supportUrls) {
        this.supportUrls = supportUrls;
    }

    public void setHelp(HelpInfo help) {
        this.help = help;
    }

    @PostConstruct
    public void printMyself() {
        // TODO check the help.file exists
        log.debug("My props: " + this);
    }
}
