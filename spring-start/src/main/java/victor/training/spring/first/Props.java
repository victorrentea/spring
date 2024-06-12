package victor.training.spring.first;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import org.springframework.validation.annotation.Validated;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Slf4j
//@Getter
@Value // = @Getter + @AllArgsConstructor + @ToString + @EqualsAndHashCode +
// face toate campurile final private
@Validated
@ConfigurationProperties(prefix = "props.bou")
public class Props {
  @NotNull
  Integer gate; // TODO set default
  @NotNull
  @NotBlank
  @Size(min = 4)
  String welcomeMessage; // TODO not null + size >= 4
  List<URL> supportUrls; // TODO size >= 1
  Map<Locale, String> contactPhones;
  Help help;

  @Data // TODO make immutable
  public static class Help {
    private Integer appId;
//    @FileExists
    private File file; // TODO file exists
    private String email; // TODO valid email
  }
//  public boolean este()// e asta nu e numit ca un getter, deci nu merge
//  @AssertTrue
//  public boolean getEFisieru() { // o "proprietate" booleana
//    return new File(help.file.getPath()).exists();
//  }

  @PostConstruct // ruleaza la startup dupa injectarea beanului curent
  public void printMyself() throws JsonProcessingException {
    String jsonToString = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
    log.info("WelcomeProps:\n" + jsonToString);
  }
}

// to test the points below, watch the log output above
//   or create a new component in which to inject WelcomeInfo and use a property
// TODO 1 inject props.welcomeMessage property in 'welcomeMessage' field
//   Hint: @Value("${
//   Experiment: what happens if the property is NOT defined ?
//     TODO 1b provide a default value by @Value("${...:defaultvalue}")
// TODO 2 inject props.gate in the 'gate' field'
//   Experiment: what happens if the actual value of the property is not a number ?
// TODO 3 inject all properties at once using @ConfigurationProperties(prefix=...)
//   Ref: https://www.google.com/search?q=configuration+properties+baledung
// TODO 4 validate (see above)
//   - using if at app startup
//   - using jakarta.validation annotations + @Validated on the class
//   Trick: provide a default to a property by assigning the field = "defaultvalue"
// TODO 5[PRO] make all these classes immutable: lombok.@Value [+ @ConstructorBinding] + @ConfigurationPropertiesScan on a @Configuration
//  Hint: https://stackoverflow.com/questions/26137932/immutable-configurationproperties
