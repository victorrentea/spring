package victor.training.spring.props;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
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
  @Bean // construiesc un obiect dintr-o librarie, pt ca nu il pot modifica sa-i
  // pun @Component eg
  @ConfigurationProperties(prefix = "api")
  public OpenAPI api() {
//    Info info = new Info();
//    info.setTitle(title); // ~> api.info.title
//    info.setVersion(version); // ~> api.info.version
//    Contact contact = new Contact();
//    contact.setName(contactName); // ~> api.info.contact.name
//    info.setContact(contact);
//    api.setInfo(info);
    OpenAPI api = new OpenAPI();
    return api;
  }
  // endregion


  //region C: inject properties via @ConfigurationProperties
//  @Bean
//  @ConfigurationProperties(prefix = "api")
//  public OpenAPI api() {
//    return new OpenAPI(); // property names need to match the field names
//      // eg: api.info.contact.name=John
//  }
  //endregion


}
