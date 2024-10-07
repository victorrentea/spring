package victor.training.spring.first;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import org.springframework.validation.annotation.Validated;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Map;

//@Slf4j
@Validated // whenever you see a @NotNull (&friends) there should be a @Validated somewhere
// tells spring to validate the properties
@ConfigurationProperties(prefix = "props")
public record Props(
  @NotNull
  @NotBlank
  String env,
  @NotNull
  @Min(0)
  Integer gate, // TODO set default
  String welcomeMessage, // TODO not null & size >= 4
  @Size(min = 1)
  List<URL> supportUrls, // TODO size >= 1
  Map<Locale, String> contactPhones,
  Help help) {

  public record Help(
      Integer appId,
//      @FileExists
      File file, // TODO file exists
      String email) // TODO valid email
  {}

//  private static final Logger log = LoggerFactory.getLogger(Props.class);

  @PostConstruct // ~ @EventListener(ApplicationReadyEvent.class)
  public void printMyself() throws JsonProcessingException {
    String json = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
    System.out.println("WelcomeProps:\n" + json);
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
