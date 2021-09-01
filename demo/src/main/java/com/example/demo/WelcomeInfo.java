package com.example.demo;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "welcome")
public class WelcomeInfo {
   private String welcomeMessage;
   private Help help;
   private List<URL> supportUrls;
   private Map<Locale, String> localContactPhone;
   private Class<?> clazz;

   public static class Help {
      private Long appId;
      private File file;

      public File getFile() {
         return file;
      }

      public Help setAppId(Long appId) {
         this.appId = appId;
         return this;
      }

      public Long getAppId() {
         return appId;
      }

      public Help setFile(File file) {
         this.file = file;
         return this;
      }

      @Override
      public String toString() {
         return "Help{" +
                "appId=" + appId +
                ", file=" + file +
                '}';
      }
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

   public Map<Locale, String> getLocalContactPhone() {
      return localContactPhone;
   }

   public WelcomeInfo setLocalContactPhone(Map<Locale, String> localContactPhone) {
      this.localContactPhone = localContactPhone;
      return this;
   }

   public WelcomeInfo setSupportUrls(List<URL> supportUrls) {
      this.supportUrls = supportUrls;
      return this;
   }

   public Class<?> getClazz() {
      return clazz;
   }

   public WelcomeInfo setClazz(Class<?> clazz) {
      this.clazz = clazz;
      return this;
   }

   @PostConstruct
   public void method() {
      System.out.println(this);
   }

   public Help getHelp() {
      return help;
   }

   public WelcomeInfo setHelp(Help help) {
      this.help = help;
      return this;
   }

   @Override
   public String toString() {
      return "WelcomeInfo{" +
             "welcomeMessage='" + welcomeMessage + '\'' +
             ", help=" + help +
             ", supportUrls=" + supportUrls +
             ", localContactPhone=" + localContactPhone +
             '}';
   }
}
