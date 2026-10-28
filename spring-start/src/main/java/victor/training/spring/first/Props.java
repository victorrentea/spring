package victor.training.spring.first;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;

import java.io.File;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Locale;
import java.util.Map;

record Props(
        String env,
        Integer gate,
        String welcomeMessage,
        List<URL> supportUrls,
        Map<Locale, String> contactPhones,
        Help help,
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
          File file,
          String email
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