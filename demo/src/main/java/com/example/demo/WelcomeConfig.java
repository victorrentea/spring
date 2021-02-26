package com.example.demo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "welcome")
public class WelcomeConfig {
   private String welcomeMessage;
   private int hostPort;
   private List<String> supportUrls;
   private Map<String, String> localContactPhone;
   private HelpInfo help;

   @Data
   static class HelpInfo {
      private URL helpUrl;
      private String iconUri;
   }
}
