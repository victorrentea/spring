package victor.training.spring.first;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@ConfigurationProperties(prefix = "props")
@Validated
public record Props(
    @NotNull
    String env,
    @NotNull
    Integer gate, // TODO set defaul,
    @Size(max = 10)
    String welcomeMessage, // TODO validate not null & size >= ,
    List<URL> supportUrls, // TODO validate size >= ,
    Map<Locale, String> contactPhones,
    Help help) {

  public record Help(
    Integer appId,
//    @FileExists // mai geek, adnotare custom ca cea de @Email, @NotNull,
    File file, // TODO validate file exists\
    @Email
    String email){ // TODO validate email pattern
  }

  @PostConstruct
  public void validateFileExists()  {
//    if (!help.file.isFile()) {
//      throw new IllegalArgumentException("Nu-i: " + help.file());
//    }
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
