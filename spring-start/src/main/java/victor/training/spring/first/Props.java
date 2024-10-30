package victor.training.spring.first;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Map;

//@Data // = @Setter + cele 4 de mai jos

//@RequiredArgsConstructor
//@Getter
//@EqualsAndHashCode
//@ToString
// === astea 4 de mai sus sunt echivalente cu @Value
// mai face si campurile private final
@Value

//@Component
@ConfigurationProperties(prefix = "props")
@Validated
//TODO stersi setterii > @Value (lombok!) > record sau  sau !
public class Props implements CommandLineRunner {
  @NotBlank
  @Size(min = 4,max = 20)
  String env; // nenul!
  int gate; // set default
  @NotNull
  String welcomeMessage; // TODO not null & size >= 4
  List<URL> supportUrls; // TODO size >= 1
  Map<Locale, String> contactPhones;
  Help help;

  @Value // TODO immutable
  public static class Help {
     Integer appId;
     File file; // TODO file exists
     String email; // TODO valid email
  }

  @Override// in joburi spring pe care le pornesti si mor dupa ce termina
  public void run(String... commandLIneArgsDeCumTeaLansat) throws Exception {
    System.out.println("Param de comanda: " + commandLIneArgsDeCumTeaLansat);
  }

  // @Transactional // nu merge aici pt ca nu e inca in contextul de Spring
//  public void printMyself() throws JsonProcessingException {

  //@PostConstruct // poate rula prea devreme intr-o aplicatie
  void crapaDacaFisierulNuExista() {
//    if (!help.file.exists()) {
//      throw new IllegalArgumentException("File not found: " + help.file);
//    }
  }

  // 💖
//  @Transactional // ar merge
  @EventListener(ApplicationStartedEvent.class) // Springule, cand te-ai pornit, da-mi un semn
  public void method() throws JsonProcessingException {
    String json = new ObjectMapper()
        .writerWithDefaultPrettyPrinter()
        .writeValueAsString(this);
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
