package victor.training.spring.props;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;


@Component
class InspectEveryBeanAtStartupTime implements BeanPostProcessor {
  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    System.out.println("Seen bean: " + beanName);
    for (Field field : bean.getClass().getDeclaredFields()) {
      System.out.println("Field : " + field.getName());
      try {
        field.setAccessible(true); // screw ?private"
        boolean hasJavaxValidations = Arrays.stream(field.getAnnotations()).anyMatch(ann -> ann.getClass().getPackageName().startsWith("javax.validation"));
// validator.validate(bean)
        if (hasJavaxValidations) {
          System.out.println("Found ya! :  " + beanName);
        }
      } catch (Exception e) {
        // swallow ex
      }
    }
    return bean;
  }
}


// For immutable version, replace below @Data and @Component with
// @Value @ConstructorBinding
//  and @ConfigurationPropertiesScan on a @Configuration
// https://stackoverflow.com/questions/26137932/immutable-configurationproperties

@Component
@ConfigurationProperties(prefix = "welcome")
//@Validated // calls up a javax.Validator to validate all the fields of this instance
// based on the javax.validation.*** annotations you used
public class WelcomeInfo {
  private static final Logger log = LoggerFactory.getLogger(WelcomeInfo.class);
  @NotNull
  private String welcomeMessage; // TODO validate is not null and size >= 4; with javax.validation annotations?
  @Size(min = 2)
  @NotNull
  private List<URL> supportUrls; // TODO validate at least 1 element
  private Map<Locale, String> localContactPhone;
  private HelpInfo help;

  @PostConstruct
  public void fileExisst() {
    // more advanced validation of config
//    if (!help.file.isFile()) {
//      throw new IllegalArgumentException("Not a file: " + help.file.getAbsolutePath());
//    }
  }

  public static class HelpInfo {
    Integer appId = -1; // default
    @NotNull
    File file; // TODO validate exists on disk

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
  }

  @PostConstruct
  public void printMyselfAtStartup() throws JsonProcessingException {
    log.info("WelcomeInfo:\n" + new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this));
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

  public Map<Locale, String> getLocalContactPhone() {
    return localContactPhone;
  }

  public void setLocalContactPhone(Map<Locale, String> localContactPhone) {
    this.localContactPhone = localContactPhone;
  }

  public HelpInfo getHelp() {
    return help;
  }

  public void setHelp(HelpInfo help) {
    this.help = help;
  }
}
