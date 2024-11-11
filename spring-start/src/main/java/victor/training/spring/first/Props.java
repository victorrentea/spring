package victor.training.spring.first;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Validated // ii zice lui Spring sa ia la puricat campurile si sa le valideze fata de anotarile de mai jos
@ConfigurationProperties(prefix = "props")

//@Getter @RequiredArgsConstructor // generate de record
public record Props(
    @NotBlank
    String env,
    @NotNull
    Integer gate, // TODO set default
    String welcomeMessage, // TODO not null & size >= 4
    List<URL> supportUrls, // TODO size >= 1
    Map<Locale, String> contactPhones,
    Help help) {

  public record Help(
      Integer appId,
      File file, // TODO file exists
      String email) { // TODO valid email
  }


//  @Transactional nu merge pe postconstruct petru ca magia asociata acestei anotari se porneste dupa postconstruct
//  @PostConstruct // ruleaza metoda dupa ce componenta e construita si toate dependintele sunt injectate


  // IoC: springu ma cheama cand vrea el
  // = Hollywood Principle: "Don't call us, we'll call you"
  @EventListener(ApplicationStartedEvent.class) // cand toata app e gata (mai tarziu)
  public void printMyself() throws JsonProcessingException {
    String json = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
    System.out.println("WelcomeProps:\n" + json);
  }
}

@Component
class IntrUnJob implements CommandLineRunner {
  @Override
  public void run(String... commandLineArgs) throws Exception {
    System.out.println("App pornita cu param " + Arrays.toString(commandLineArgs));
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
