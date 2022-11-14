package victor.training.spring.properties;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Value("${api.info.version}")
    String version;
    @Value("${api.info.title}")
    String title;
    @Value("${api.info.contact.name}")
    String contactName; // + 10 more

    @Bean
    //    @ConfigurationProperties("api")
    // Any available property starting with "api." is injected in any property(getter/setter) of the returned object
    // eg: api.info.version=1.0  -> api().setInfo(new Info().setVersion("1.0"))
    public OpenAPI api() {
        return new OpenAPI().info(new Info()
                .title(title)
                .version(version)
                .contact(new Contact().name(contactName)));
    }
}
