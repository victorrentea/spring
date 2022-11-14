package victor.training.spring.props;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    // (A) Inject properties and manually configure an external class (OpenAPI)
    @Value("${api.info.title}")
    String title;
    @Value("${api.info.version}")
    String version;
    @Value("${api.info.contact.name}")
    String contactName; // + 10 more

    @Bean
    // (B) Auto-inject properties in @Bean
    //    @ConfigurationProperties("api")
    // Any available property starting with "api." is injected in any property(getter/setter) of the returned object
    // eg: api.info.version=1.0  -> api().setInfo(new Info().setVersion("1.0"))
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
}
