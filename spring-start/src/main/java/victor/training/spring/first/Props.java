package victor.training.spring.first;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Map;


@Validated
@ConfigurationProperties(prefix = "props")
public class Props { // spring-managed bean
  //  @Value("${props.gate:1}") // default - Victor hates this as it feels like a backdoor/hack that no one should know about
//  @Value("${props.gate}") // !! YEEHAAA!: fails to start if that is not defined
  @NotNull // declaring the field as not null
  private final Integer gate; // TODO set default
  @NotBlank
  private final String welcomeMessage; // TODO not null + size >= 4
  @Size(min = 1)
  private final List<URL> supportUrls; // TODO size >= 1
  private final Map<Locale, String> contactPhones;
  private final Help help;



  public Props(Integer gate, String welcomeMessage, List<URL> supportUrls, Map<Locale, String> contactPhones, Help help) {
//    this.gate = Objects.requireNonNull(gate); // bad error
    this.gate = gate; // bad error
    this.welcomeMessage = welcomeMessage;
    this.supportUrls = supportUrls;
    this.contactPhones = contactPhones;
    this.help = help;
  }
  public static class Help {
    private final Integer appId;
    private final File file; // TODO file exists
    private final String email; // TODO valid email

    public Help(Integer appId, File file, String email) {
      this.appId = appId;
      this.file = file;
      this.email = email;
    }

    public File getFile() {
      return file;
    }

    public Integer getAppId() {
      return appId;
    }

    public String getEmail() {
      return email;
    }
  }
   public Help help() {
    return help;
  }


//  @PostConstruct
//  public void check() {
//    if (gate == null) {
//      throw new IllegalStateException("gate is mandatory");
//    }
//  }


  @PostConstruct // called after the bean creation by Spring
  public void printMyself() throws JsonProcessingException {
    String jsonToString = new ObjectMapper().writerWithDefaultPrettyPrinter()
        .writeValueAsString(this);
    log.info("WelcomeProps:\n" + jsonToString);
  }


  private static final Logger log = LoggerFactory.getLogger(Props.class);

  public Integer gate() {
    return gate;
  }

  public String welcomeMessage() {
    return welcomeMessage;
  }

  public List<URL> supportUrls() {
    return supportUrls;
  }

  public Map<Locale, String> contactPhones() {
    return contactPhones;
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
