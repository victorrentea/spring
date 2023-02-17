package victor.training.spring.gateway;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import static java.time.temporal.ChronoUnit.MINUTES;

@Slf4j
public class LoginViaQueryParamFilter implements GatewayFilter {
  @Value("${jwt.signature.shared.secret.base64}")
  String jwtSecret;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    MultiValueMap<String, String> params = exchange.getRequest().getQueryParams();
    if (!params.containsKey("user")) {
      if (exchange.getRequest().getCookies().containsKey("Bearer")) {
        String jwtToken = exchange.getRequest().getCookies().get("Bearer").get(0).getValue();
        ServerHttpRequest mutatedRequest = exchange.getRequest().mutate().header("Authorization", "Bearer " + jwtToken).build();
        log.info("Added authorization to request headers from COOKIE: " + mutatedRequest.getHeaders());
        return chain.filter(exchange.mutate().request(mutatedRequest).build());
      }

      return chain.filter(exchange);
    }
    String username = params.getFirst("user");

    Algorithm algorithm = Algorithm.HMAC256(Base64.getDecoder().decode(jwtSecret));

    String jwtToken = JWT.create()
            .withIssuer("Victor")
            .withSubject(username)
            .withClaim("role", username.toUpperCase())
            .withClaim("country", "RO")
            .withIssuedAt(Instant.now())
            .withExpiresAt(Instant.now().plus(5, MINUTES))
            .withJWTId(UUID.randomUUID().toString())
            .sign(algorithm);

    ServerHttpRequest mutatedRequest = exchange.getRequest().mutate().header("Authorization", "Bearer " + jwtToken).build();
    exchange.getResponse().getCookies().put("Bearer", List.of(ResponseCookie.from("Bearer", jwtToken).build()));
    log.info("Added authorization to request headers: " + mutatedRequest.getHeaders());

    return chain.filter(exchange.mutate().request(mutatedRequest).build());
  }
}
