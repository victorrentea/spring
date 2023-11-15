package victor.training.spring.gateway;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.*;

import static java.time.temporal.ChronoUnit.MINUTES;

@Slf4j
@Component
public class QueryParamToJwtToken implements GatewayFilter {
  public static final String COOKIE_NAME = "Bearer";
  @Value("${jwt.signature.shared.secret.base64}")
  String jwtSecret;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    String userOnUrl = Utils.extractUserFromUrl(exchange.getRequest().getQueryParams());

    if (userOnUrl!=null) {
      String jwtToken = generateJwtToken(userOnUrl, exchange.getRequest().getQueryParams().toSingleValueMap());
      log.info("Using Bearer from QUERY PARAMS (+SetCookie) to {}: payload={}, raw={}",
              exchange.getRequest().getPath(), extractPayload(jwtToken), jwtToken);
      exchange.getResponse().getCookies().put(COOKIE_NAME, List.of(ResponseCookie.from(COOKIE_NAME, jwtToken).build()));
      ServerHttpRequest mutatedRequest = addBearerToRequest(exchange.getRequest(), jwtToken);
      return chain.filter(exchange.mutate().request(mutatedRequest).build());
    }
    if (exchange.getRequest().getCookies().containsKey(COOKIE_NAME)) {
      String jwtToken = exchange.getRequest().getCookies().get(COOKIE_NAME).get(0).getValue();
      log.info("Using Bearer from COOKIE to {}: payload={}, raw={}",
              exchange.getRequest().getPath(), extractPayload(jwtToken), jwtToken);
      ServerHttpRequest mutatedRequest = addBearerToRequest(exchange.getRequest(), jwtToken);
      return chain.filter(exchange.mutate().request(mutatedRequest).build());
    }

    return chain.filter(exchange);
  }

  private static ServerHttpRequest addBearerToRequest(ServerHttpRequest request, String jwtToken) {
    return request.mutate()
        .header("Authorization", "Bearer " + jwtToken).build();
  }

  private String extractPayload(String jwtToken) {
    return new String(Base64.getDecoder().decode(jwtToken.split("\\.")[1]));
  }

  private String generateJwtToken(String userOnUrl, Map<String, String> extraClaims) {
    Algorithm algorithm = Algorithm.HMAC256(Base64.getDecoder().decode(jwtSecret));
    Builder jwtBuilder = JWT.create()
            .withIssuer("Victor")
            .withSubject(userOnUrl)
            .withIssuedAt(Instant.now())
            .withExpiresAt(Instant.now().plus(5, MINUTES))
            .withJWTId(UUID.randomUUID().toString());
    addExtraClaimsFromQueryParams(jwtBuilder, extraClaims);
    return jwtBuilder
            .withClaim("authorities", userOnUrl.toUpperCase().equals("ADMIN")?List.of("training.delete"):List.of())
            .withClaim("role", userOnUrl.toUpperCase())
            .withClaim("country", "RO")
            .sign(algorithm);
  }

  private static void addExtraClaimsFromQueryParams(Builder jwtBuilder, Map<String, String> extraClaims) {
    Map<String, String> map = new HashMap<>(extraClaims);
    map.remove("u");
    map.remove("user");
    map.forEach(jwtBuilder::withClaim);
  }

}
