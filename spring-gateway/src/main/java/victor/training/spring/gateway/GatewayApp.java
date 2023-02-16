package victor.training.spring.gateway;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@SpringBootApplication
@Slf4j
public class GatewayApp {
  public static void main(String[] args) {
      SpringApplication.run(GatewayApp.class, args);
  }

  @Value("${jwt.signature.shared.secret.base64}")
  String jwtSecret;

  @Bean
  public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
    return builder.routes()
            .route("path_route", r -> r.path("/**")
                    .filters(f -> f.filters(new GatewayFilter() {
                      @Override
                      public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                        MultiValueMap<String, String> params = exchange.getRequest().getQueryParams();
                        if (params.containsKey("user")) {
                          String username = params.getFirst("user");
                          String bearerToken = Jwts.builder()
                                          .setSubject(username)
                                          .claim("country", "Country")
                                          .signWith(SignatureAlgorithm.HS512, jwtSecret)
                                          .compact();
                          exchange.getRequest().getHeaders().put("Authorization", List.of("Bearer " + bearerToken));
                          log.debug("Added authorization to request headers: " + exchange.getRequest().getHeaders());
                        }
                        return chain.filter(exchange);
                      }
                    }))
                    .uri("http://localhost:8080"))
            .build();
  }

}
