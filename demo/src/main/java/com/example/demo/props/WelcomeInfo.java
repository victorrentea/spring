package com.example.demo.props;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Slf4j
@ToString
@Component
public class WelcomeInfo {
    @Value("${welcome.welcomeMessage}")
    private String welcomeMessage;
    private List<String> supportUrls;
    private Map<String,String> localContactPhone; // per country
    // TODO HelpInfo help;

    @PostConstruct
    public void printMyself() {
        log.info("My props: " + this);
    }
}
