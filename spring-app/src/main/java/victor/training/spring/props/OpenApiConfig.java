package victor.training.spring.props;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
  @Value("${api.info.title}")
  String title;
  @Value("${api.info.version}")
  String version;
  @Value("${api.info.contact.name}")
  String contactName; // + 10 more

  // Configures the metadata of the published OpenAPI contract at
  // http://localhost:8080/v3/api-docs

  // region A: fill properties, classic style
  @Bean
  public OpenAPI api() {
    Info info = new Info();
    info.setTitle(title);
    info.setVersion(version);
    Contact contact = new Contact();
    contact.setName(contactName);
    info.setContact(contact);
    OpenAPI api = new OpenAPI();
    api.setInfo(info);
    return api;
  }
  // endregion


  //region B: fill properties, fluent style
//  @Bean
//  public OpenAPI api() {
//    return new OpenAPI()
//        .info(new Info()
//            .title(title)
//            .version(version)
//            .contact(new Contact()
//                .name(contactName)));
//  }
  //endregion


  //region C: inject properties via @ConfigurationProperties
//  @Bean
//  @ConfigurationProperties(prefix = "api")
//  public OpenAPI api() {
//    return new OpenAPI(); // property names need to match the field names
//      // eg: api.info.contact.name=John
//  }
  //endregion


}
