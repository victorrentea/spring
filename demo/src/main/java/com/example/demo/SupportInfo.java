package com.example.demo;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "welcome")
public class SupportInfo {
   private String welcomeMessage;
   private HelpInfo help;

   @Data
   public static class HelpInfo {
      private URL url;
      private String iconUri;
   }
   private List<SupportUrl> supportUrls;
   @Data
   public static class SupportUrl {
      private URL url;
      private String purpose;
   }
   private Map<String, String> phones;

   @PostConstruct
   public void printMe() {
      System.out.println(this);
   }
}
