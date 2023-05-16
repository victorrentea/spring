package victor.training.spring.props;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Map;

// For immutable version, replace below @Data and @Component with
// @Value @ConstructorBinding
//  and @ConfigurationPropertiesScan on a @Configuration
// https://stackoverflow.com/questions/26137932/immutable-configurationproperties

@Slf4j
@Data
@Component // (A) mutable
// @Value @ConstructorBinding // (B) immutable
@ConfigurationProperties(prefix = "welcome")
public class WelcomeInfo {
  int gate;
  String welcomeMessage; // TODO validate is not null and size >= 4; with javax.validation annotations?
  List<URL> supportUrls; // TODO validate at least 1 element
  Map<Locale, String> localContactPhone;
  HelpInfo help;

  @Data // (A) mutable
  // @Value // (B) immutable
  public static class HelpInfo {
    Integer appId;
    File file; // TODO validate exists on disk
  }

  @PostConstruct
  public void printMyselfAtStartup() throws JsonProcessingException {
    // log.info("WelcomeInfo:\n" + new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this));
  }
}
