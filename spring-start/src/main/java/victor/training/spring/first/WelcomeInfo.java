package victor.training.spring.first;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Map;

// TODO #1 injectati "Welcome to This Spring Realm"
//  din proprietati in campul 'welcomeMessage' din clasa asta
//  hint: foloseste @Value("${...}") din spring
//  hint: fa clasa dectabila de catre spring

// TEST: pornesti FirstApplication ca program java si in consola
// tre sa vezi WelcomeInfo: ....
@Slf4j
@Component
@Data
@ConfigurationProperties(prefix = "welcome")
@Validated
public class WelcomeInfo {
//@NonNull // lombok adauga IFuri prin setteri/constructori care arunca NPE direct
  @NotNull // javax.validation
  String welcomeMessage; // TODO validate is not null and size >= 4; with javax.validation annotations?
 @NotEmpty
  List<URL> supportUrls; // TODO validate at least 1 element
  Map<Locale, String> localContactPhone;
  HelpInfo help;

  @Data
  public static class HelpInfo {
    Integer appId;
    File file; // TODO validate exists on disk
  }

  @AssertTrue
  public boolean helpFileExists() {
return help.file.isFile();
  }

  @PostConstruct
  public void printMyselfAtStartup() throws JsonProcessingException {
//    if (!help.file.isFile()) {
//      throw new IllegalArgumentException("Nu-i fisieru");
//    }
     log.info("WelcomeInfo:\n" + new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this));
  }
}
