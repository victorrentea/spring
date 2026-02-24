package victor.training.spring.first;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.event.EventListener;
import org.springframework.validation.annotation.Validated;

import java.io.File;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

@Validated
@ConfigurationProperties(prefix = "props")
record Props(
        String env,
        @NotNull
        Integer gate,
        @NotNull
        @Size(min=3)
        String welcomeMessage,
        List<URL> supportUrls,
        Map<Locale, String> contactPhones,
        @Valid
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
//          @Pattern("[a-zA-Z1-0]+")
          @Email
          String email
  ) {
  }

//  @PostConstruct // hook method telling spring to call it after initialization of THIS bean
  @EventListener(ApplicationStartedEvent.class) // run it after the entire app is started
  public void printMyselfAtStartup() throws JsonProcessingException {
//    Objects.requireNonNull(gate);
//    Objects.requireNonNull(welcomeMessage);

    String json = new ObjectMapper()
            .findAndRegisterModules()
            .writerWithDefaultPrettyPrinter()
            .writeValueAsString(this);
    System.out.println("Props:\n" + json);
  }
}