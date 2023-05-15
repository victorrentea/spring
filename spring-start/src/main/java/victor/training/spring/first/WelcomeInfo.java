package victor.training.spring.first;

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

// TODO inject properties from yml in these fields
@Slf4j
public class WelcomeInfo {
  String welcomeMessage; // TODO validate is not null and size >= 4; with javax.validation annotations?
  List<URL> supportUrls; // TODO validate at least 1 element
  Map<Locale, String> localContactPhone;
  HelpInfo help;

  public static class HelpInfo {
    Integer appId;
    File file; // TODO validate exists on disk
  }

  @PostConstruct
  public void printMyselfAtStartup() throws JsonProcessingException {
     log.info("WelcomeInfo:\n" + new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this));
  }
}
