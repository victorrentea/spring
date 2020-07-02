package com.example.demo.props;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@ToString
@Component
@Data
@ConfigurationProperties(prefix = "welcome")
public class WelcomeInfo {
    private String welcomeMessage;
    private List<String> supportUrls;
    private Map<String,String> localContactPhone; // per country
    private HelpInfo help;
//    private List<?> wsClients = new ArrayList<>();

    @PostConstruct
    public void printMyself() {
        log.info("My props: " + this);
    }
}
@Data
class HelpInfo {
    private URL helpUrl;
    private String iconUri;
}

//@Data
//class ClientConfig {
//    private URL url;
//    private String name;
//}

