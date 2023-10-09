package victor.training.spring.first;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Component
@RequiredArgsConstructor
class TuOmSimpluCandVreiConfig {
  private final WelcomeInfo welcomeInfo;

  public void method() {
//    welcomeInfo.setWelcomeMessage("TZAPA!");
  }

}

@Slf4j
@Getter // generates getters + setters
@ConstructorBinding
@ConfigurationProperties(prefix = "welcome")
@Validated
@RequiredArgsConstructor
public class WelcomeInfo {
  private final int gate;
  @NotNull
  @NotBlank
  private final String welcomeMessage;
  @Size(min = 1)
  @NotNull
  private final List<URL> supportUrls;
  private final Map<Locale, String> localContactPhone;
  private final HelpInfo help;

  @Value // in lombok: clasa o imutabila: @Getter @RAC toate campurile finale si private hashcode tostring equals
  public static class HelpInfo {
    Integer appId;
//    @FileExists
    File file; // TODO 4c validate exists on disk
  }

  @PostConstruct
  public void atStartup() {
//    if (!help.file.isFile()) {
//      throw new IllegalArgumentException("Nu-i! " + help.file);
//    }
  }
  @PostConstruct
  public void printMyselfAtStartup() throws JsonProcessingException {
    String jsonToString = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
    log.info("WelcomeInfo:\n" + jsonToString);
  }
}
// to test the points below, watch the log for 'WelcomeInfo:' output
//   or create a new component in which to inject WelcomeInfo and use a property
// TODO 1 inject welcome.welcomeMessage property in 'welcomeMessage' field
//   Hint: @Value("${
//   Question: what happens if the property is NOT defined ?
//     TODO 1* provide a default value by @Value("${...:defaultvalue}")
// TODO 2 inject welcome.gate in the 'gate' field'
//   Question: what happens if the value of the property is not an INT ?
// TODO 3 inject all properties at once using @ConfigurationProperties(prefix=...)
//   Ref: https://www.google.com/search?q=configuration+properties+baledung
// TODO 4 validate (see above)
//   - using if at startup
//   - using javax.validation annotations + @Validated on the class
//   Trick: provide a default to a property by assigning the field = "defaultvalue"
// TODO 5[PRO] make this class immutable: lombok.@Value + @ConstructorBinding + @ConfigurationPropertiesScan on a @Configuration
//  Hint: https://stackoverflow.com/questions/26137932/immutable-configurationproperties
