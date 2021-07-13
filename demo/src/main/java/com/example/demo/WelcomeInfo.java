package com.example.demo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Map;

@Slf4j
@Data // getters & setters + toString
@Component
@ConfigurationProperties(prefix = "welcome")
public class WelcomeInfo {
    private String welcomeMessage;
    private List<URL> supportUrls;
    private Map<String,String> localContactPhone; // per country
    private HelpInfo help;

    @Data
    public static class HelpInfo {
        private Integer appId;
        private File file;
        private Class<?> clazz;
    }

    @PostConstruct
    public void printMyself() {
        // TODO check the help.file exists
        log.debug("My props: \n" + this);
    }
}
