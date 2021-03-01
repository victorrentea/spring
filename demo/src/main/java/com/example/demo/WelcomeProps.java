package com.example.demo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "welcome")
public class WelcomeProps {

   private String welcomeMessage;
   private List<URL> supportUrls;
   private Map<Locale, String> localContactPhone;
   private HelpInfo help;
   @Data
   public static class HelpInfo {
      private URL helpUrl;
      private String iconUri;
   }

   @PostConstruct
   public void method() {
      System.out.println("Eu sunt: " + this);
   }
}
