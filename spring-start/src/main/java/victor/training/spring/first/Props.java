package victor.training.spring.first;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Data // generates getters & setters
@Component
public class Props {
  private String env;
  private Integer gate; // TODO set default
  private String welcomeMessage; // TODO validate not null & size >= 4
  private List<URL> supportUrls; // TODO validate size >= 1
  private Map<Locale, String> contactPhones;
  private Help help;

  @Data // TODO immutable
  public static class Help {
    private Integer appId;
    private File file; // TODO validate file exists
    private String email; // TODO validate email pattern
  }

  @PostConstruct
  public void printMyself() throws JsonProcessingException {
    String json = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
    System.out.println("Props:\n" + json);
  }
}


// to test the points below, watch the log output above
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
//   - using jakarta.validation annotations + @Validated on the class
//   Trick: provide a default to a property by assigning the field = "defaultvalue"
// TODO 5[PRO] make this class immutable: lombok.@Value + @ConstructorBinding + @ConfigurationPropertiesScan on a @Configuration
//  Hint: https://stackoverflow.com/questions/26137932/immutable-configurationproperties
