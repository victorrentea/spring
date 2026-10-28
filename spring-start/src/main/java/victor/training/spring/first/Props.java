package victor.training.spring.first;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.io.File;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Validated
@ConfigurationProperties(prefix = "props")
record Props(
        String env,
        Integer gate,
        @NotBlank String welcomeMessage,
        @NotEmpty List<URL> supportUrls,
        Map<Locale, String> contactPhones,
        @Valid Help help,
        Map<TenantEnum, TenantConfig> tenants
) {
  enum TenantEnum {FR, RO} //  ⚠️ app may fail to start if config mentions an unknown value ('ES')

  record TenantConfig(
          String greeting,  // TODO set default value
          Duration lunch
  ) {
  }

  record Help(
          Integer appId,
          @FileExists File file,
          @Email String email
  ) {
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