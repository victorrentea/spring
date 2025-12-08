package victor.training.spring.first;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    @NotEmpty
    String env,
    @NotNull
    Integer gate,
    String welcomeMessage,
    List<URL> supportUrls,
    Map<Locale, String> contactPhones,
    @Valid// cascadeaza validarea lui Props pe Help
    Help help,
    Map<TenantEnum, TenantConfig> tenants
){
  enum TenantEnum {FR, RO} //  ⚠️ app may fail to start if config mentions an unknown value ('ES')
  record TenantConfig(
      String greeting,  // TODO set default value
      Duration lunch
  ) {}

  record Help(
      Integer appId,
//      @FileExists
      File file,
      String email
  ){}

//  @AssertTrue(message = "File exists")
  public boolean isFileExists() {
    // validari mai complexe
    return help.file.exists();
  }

//  @PostConstruct
//  void fileExists() {
//    if (!help.file.exists()) {
//      throw new RuntimeException("Nu-i: " + help.file());
//    }
//  }

  @PostConstruct // IoC lifecycle hook
  public void printMyselfAtStartup() throws JsonProcessingException {
    String json = new ObjectMapper()
        .findAndRegisterModules()
        .writerWithDefaultPrettyPrinter()
        .writeValueAsString(this);
    System.out.println("Props:\n" + json);
  }
}