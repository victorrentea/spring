package victor.training.spring.props;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import org.springframework.validation.annotation.Validated;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Map;

// TODO map all properties starting with props.* from application.properties to this object's fields
// TODO check the print happening at app startup
@ConfigurationProperties(prefix = "props")
@Validated // DevEx++: ca sa fie eroare mai developer-friendly
public record Props(
  String env,
  @NotNull
  Integer gate, // TODO experiment: what happens if config is missing
  String welcomeMessage, // TODO validate not null & size >= 4
  @Size(min = 1)
  @NotNull
  List<URL> supportUrls, // TODO validate has at least 1 element >= 1
  Map<Locale, String> contactPhones,
  Help help
) {
  public record Help(
    Integer appId, //poate ramane null
    File file,
    String email // TODO validate email has valid pattern
  ) {}

  @PostConstruct // ruleaza dupa initul instantei de catre Spring
  // Hollywood Principle: don't call us, we'll call you;
  // = Inversion of Control  (IoC)
  public void printMyself() throws JsonProcessingException {
    String json = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
    System.out.println("Props:\n" + json);
  }
}
