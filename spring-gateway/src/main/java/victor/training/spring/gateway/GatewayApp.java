package victor.training.spring.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
public class GatewayApp {
  public static void main(String[] args) {
    SpringApplication.run(GatewayApp.class, args);
  }


  @Bean
  public RouteLocator customRouteLocator(RouteLocatorBuilder builder,
                                         QueryParamToJwtToken queryParamFilter,
                                         QueryParamPreAuthHeaders preAuthHeadersFilter) {
    return builder.routes()
            .route("path_route", r -> r.path("/**")
                    .filters(f -> f.filters(queryParamFilter, preAuthHeadersFilter))
                    .uri("http://localhost:8080"))
            .build();
  }

}
