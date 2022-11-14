package victor.training.spring.properties;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class PropertiesApp {
    public static void main(String[] args) {
        SpringApplication.run(PropertiesApp.class, args);
    }

    @Value("${prop}")
    private String prop;

    @EventListener(ApplicationStartedEvent.class)
    public void method() {
        System.out.println("Prop from a file: " + prop);
    }

    @Bean
      public OpenAPI getOpenApiDocumentation() {
        Info info = info();
        System.out.println("version: " + info.getVersion());
        System.out.println("version: " + info.getTitle());
        return new OpenAPI().info(info);
    }

    @Bean
    @ConfigurationProperties("api.common")
    public Info info() {
        return new Info();
    } // after you return an empty object, spring will inejct any property under 'api.common. prefix into the propertieso
    // of the returned INfo object (you have to have getters and setters for each of those properties)
}
// @Value("${api.common.version}")         String apiVersion;
//  @Value("${api.common.title}")           String apiTitle;
//  @Value("${api.common.description}")     String apiDescription;
//  @Value("${api.common.termsOfService}")  String apiTermsOfService;
//  @Value("${api.common.license}")         String apiLicense;
//  @Value("${api.common.licenseUrl}")      String apiLicenseUrl;
//  @Value("${api.common.externalDocDesc}") String apiExternalDocDesc;
//  @Value("${api.common.externalDocUrl}")  String apiExternalDocUrl;
//  @Value("${api.common.contact.name}")    String apiContactName;
//  @Value("${api.common.contact.url}")     String apiContactUrl;
//  @Value("${api.common.contact.email}")   String apiContactEmail;
//
//  /**
//  * Will exposed on $HOST:$PORT/swagger-ui.html
//  *
//  * @return the common OpenAPI documentation
//  */
//  @Bean
//  public OpenAPI getOpenApiDocumentation() {
//    return new OpenAPI()
//      .info(new Info()
//        .title(apiTitle)
//        .description(apiDescription)
//        .version(apiVersion)
//        .contact(new