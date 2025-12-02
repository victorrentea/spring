package victor.training.spring.first;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.context.event.EventListener;
import org.springframework.validation.annotation.Validated;

import java.io.File;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Validated
@ConfigurationProperties(prefix = "props")
record Props( //❤️
    @NotNull
    String env,
    Integer gate,
    @NotBlank // can @Entity
    String welcomeMessage,
    @Size(min = 3)
    List<URL> supportUrls,
    Map<Locale, String> contactPhones,
    @Valid // valideaza-i si campurile lui asta
    Help help,
    Map<TenantEnum, TenantConfig> tenants,
    List<TenantConfig> tenantsList
){
  enum TenantEnum {FR, RO} //  ⚠️ app may fail to start if props mention unknown value ('ES')
  record TenantConfig(
      String greeting,  // TODO set default value
      Duration lunch
  ) {}

  record Help(
      Integer appId,
      @FileExists // adnotarea mea, pt ca pot 💪
      File file,
      String email
  ){}

//  @EventListener(ApplicationStartedEvent.class) // cand toate beanurile-s gata
//  @PostConstruct // cand e beanul tau gata = mai repede
//  public void checkFileExists() {
//    if (!help.file.exists()) {
//      throw new IllegalArgumentException("Nu-i: " + help.file);
//    }
//  }


  @PostConstruct
  public void printMyselfAtStartup() throws JsonProcessingException {
    String json = new ObjectMapper()
        .findAndRegisterModules()
        .writerWithDefaultPrettyPrinter()
        .writeValueAsString(this);
    System.out.println("Props:\n" + json);
  }
}