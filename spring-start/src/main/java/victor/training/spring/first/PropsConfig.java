package victor.training.spring.first;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.event.EventListener;

@Import(Client.class)
public class PropsConfig {
  @Bean
  @ConfigurationProperties(prefix="props")
  public MatteosConfigFromLibrary config(
//      @Value("${props.gate}") String gate,
//      @Value("${props.welcome-message}") String welcomeMessage
  ) {
    return new MatteosConfigFromLibrary()
//        .setGate(gate)
//        .setWelcomeMessage(welcomeMessage)
        ;
  }

}

class Client {
  private final MatteosConfigFromLibrary config;

  Client(MatteosConfigFromLibrary config) {
    this.config = config;
  }

//  @Transactional //does not work here
//  @PostConstruct
  @EventListener(ApplicationStartedEvent.class)
  public void method() {
    System.out.println("The config is " + config.getGate() + " and " + config.getWelcomeMessage());
  }
}

class MatteosConfigFromLibrary {
  private String welcomeMessage;
  private String gate;

  public String getGate() {
    return gate;
  }

  public String getWelcomeMessage() {
    return welcomeMessage;
  }

  public MatteosConfigFromLibrary setGate(String gate) {
    this.gate = gate;
    return this;
  }

  public MatteosConfigFromLibrary setWelcomeMessage(String welcomeMessage) {
    this.welcomeMessage = welcomeMessage;
    return this;
  }
}