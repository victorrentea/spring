package victor.training.spring.first;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.*;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

import jakarta.annotation.PostConstruct;
import org.springframework.validation.annotation.Validated;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Slf4j
@Value // = getters +ctor
//@Component
@ConfigurationProperties(prefix = "props")
@Validated
public class Props {
  @NotNull // adnotarile de validare nu merg decat daca TU CERI sa fie verificate
  // 1) @Validated (aici pe clasa, in REST API pe @RequestBody, sau pe param oricarei metode)
  // 2) jpaRepo.save( -> automat se verifica @ pe @Entity de hibernate
  // 3) validator.validate(objAdnotat); dar iti trebuie sa-ti injectezi un Validator 🤢🤢
  Integer gate;
  @NotBlank
  String welcomeMessages; // TODO not null + size >= 4
  @NotEmpty
  List<URL> supportUrls;
  Map<Locale, String> contactPhones;
  Help help;

  public record Help(
      Integer appId,
      File file,
      @Email String email) {
  }

  @PostConstruct // ruleaza dupa injectia dep/prop
  public void printMyself() throws JsonProcessingException {
//    if (welcomeMessage == null) {
//      throw new IllegalArgumentException();
//    }
    // validarea cu IFuri e de evitat pt checkuri simple
//    Objects.requireNonNull(welcomeMessage, "message");
//    Objects.requireNonNull(gate, "gate"); // daca ambele lipsesc vei vedea DOAR PRIMA eroare

    String jsonToString = new ObjectMapper().writerWithDefaultPrettyPrinter()
        .writeValueAsString(this);
    log.info("WelcomeProps:\n" + jsonToString);
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
