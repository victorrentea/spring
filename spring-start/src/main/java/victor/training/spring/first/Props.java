package victor.training.spring.first;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.io.File;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Validated
@ConfigurationProperties(prefix="props")
public record Props(
        String env,
        @NotNull
        Integer gate,
        @NotBlank
        String welcomeMessage,
        List<URL> supportUrls,
        @Size(min=1)
        Map<Locale, String> contactPhones,
        @Valid //  ia in calcul @ de validare de pe obiectul acestui camp
        Help help,
        List<Class> processors,
        Map<TenantEnum, TenantConfig> tenants
) {
  enum TenantEnum {FR, RO} //  ⚠️ app may fail to start if config mentions an unknown value ('ES')

  record TenantConfig(
          String greeting,  // TODO set default value
          Duration lunch
  ) {
  }
  // daca env == "prod" ⇒ sa ai minim 2 contact phones

  record Help(
          Integer appId,
          File file,
          @Email
          String email
  ) {
  }

  @AssertTrue(message = "In production, you must have at least 2 contact phones")
  public boolean hasMinContactPhones() {
    return !"prod".equals(env) || contactPhones.size() >= 2;
  }

  @PostConstruct
  public void printMyselfAtStartup() throws JsonProcessingException {
    String json = new ObjectMapper()
            .findAndRegisterModules()
            .writerWithDefaultPrettyPrinter()
            .writeValueAsString(this);
    System.out.println("Props:\n" + json);
  }
}