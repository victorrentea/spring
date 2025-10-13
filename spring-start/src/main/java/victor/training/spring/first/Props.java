package victor.training.spring.first;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.*;
import lombok.Data;
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

// TODO
//  - set default env = prod
//  - validate welcomeMessage not null and min 4 chars
//  - validate supportUrl.size >= 1
//  - validate file exists
//  - validate email is valid
@Validated
@ConfigurationProperties(prefix = "props")
public record Props(
    String env,
    Integer gate,
    @NotBlank
    String welcomeMessage,
    @NotEmpty
    List<URL> supportUrls,
    Map<Locale, String> contactPhones,
    Help help
) {

  public record Help(
      Integer appId,
      File file,
      @Email
      String email
  ) {}

  @PostConstruct
  public void printMyself() throws JsonProcessingException {
//    if (!help.file.exists()) {
//      throw new IllegalArgumentException("File does not exist: " + help.file.getAbsolutePath());
//    }
    String json = new ObjectMapper()
        .writerWithDefaultPrettyPrinter()
        .writeValueAsString(this);
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
