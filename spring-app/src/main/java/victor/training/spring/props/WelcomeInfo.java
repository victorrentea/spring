package victor.training.spring.props;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Map;

// For immutable version, replace below @Data and @Component with
// @Value @ConstructorBinding
//  and @ConfigurationPropertiesScan on a @Configuration
// https://stackoverflow.com/questions/26137932/immutable-configurationproperties

//@Component
//class DirectValue {
//  @Value("${welcome.welcomeMessage:8}") // fails if the prop is not defined
//  private String welcomeMessage;
//  @PostConstruct
//  public void method() {
//    System.out.println("message=" + welcomeMessage);
//  }
//}

@Slf4j
@Data
@Component
@ConfigurationProperties(prefix = "welcome")
@Validated
public class WelcomeInfo {
  @NotNull
  String welcomeMessage = "surprise"; // TODO validate is not null and size >= 4; with javax.validation annotations?
  //  @NotEmpty
  @Size(min = 2)
  List<URL> supportUrls; // TODO validate at least 2 element
  Map<Locale, String> localContactPhone;
  HelpInfo help;

  @Data
  public static class HelpInfo {
    Integer appId;
    File file; // TODO validate exists on disk
  }

  @AssertTrue
  public boolean hasHelpFile() {
    System.out.println("Called!!!");
    return help.file.isFile();
  }

  @PostConstruct
  public void printMyselfAtStartup() throws JsonProcessingException {
//    if (!help.file.isFile()) {
//      throw new IllegalArgumentException("Oups: no file:  " + help.file);
//    }
    log.info("WelcomeInfo:\n" + new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this));
  }
}
