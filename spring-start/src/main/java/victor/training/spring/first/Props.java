package victor.training.spring.first;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
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
record Props( //❤️ record ca orice Dto ±@Builder (lombok)
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
//      @FileExists // adnotarea mea, pt ca pot 💪
      File file,
      String email
  ){}

//  @EventListener(ApplicationStartedEvent.class) // cand toate beanurile-s gata
//  @PostConstruct // cand e beanul tau gata = mai repede. era mai bine cu assertTrue - sar toate erorile odata
//  public void checkFileExists() {
//    if (!help.file.exists()) {
//      throw new IllegalArgumentException("Nu-i: " + help.file);
//    }
//  }
  @AssertTrue(message = "File must exist") // ❤️
  @JsonIgnore // ⚠️daca esti pe DTO, sa nu creada swagger ca ai un camp
  public boolean isFileExist() { // cu is-
    // MUST have la multi-field validations; daca field1==DRAFT => field2 != null
    if (help.file==null) return true;
    return help.file.exists();
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