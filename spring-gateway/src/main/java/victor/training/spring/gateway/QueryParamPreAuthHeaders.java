package victor.training.spring.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

import static victor.training.spring.gateway.Utils.extractUserFromUrl;

@Slf4j
@Component
public class QueryParamPreAuthHeaders implements GatewayFilter {

  public static final String COOKIE_NAME = "X-User";

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    String userOnUrl = extractUserFromUrl(exchange.getRequest().getQueryParams());

    if (userOnUrl != null) {
      log.info("Using PreAuthUser from QUERY PARAMS (+SetCookie) to {}: {}", exchange.getRequest().getPath(), userOnUrl);
      exchange.getResponse().getCookies().put(COOKIE_NAME, List.of(ResponseCookie.from(COOKIE_NAME, userOnUrl).build()));
      ServerHttpRequest mutatedRequest = addRequestHeaders(exchange, userOnUrl);
      return chain.filter(exchange.mutate().request(mutatedRequest).build());
    }
    if (exchange.getRequest().getCookies().containsKey(COOKIE_NAME)) {
      String userInCookie = exchange.getRequest().getCookies().get(COOKIE_NAME).get(0).getValue();
      log.info("Using PreAuthUser from COOKIE (+SetCookie) to {}: {}", exchange.getRequest().getPath(), userInCookie);
      ServerHttpRequest mutatedRequest = addRequestHeaders(exchange, userInCookie);
      return chain.filter(exchange.mutate().request(mutatedRequest).build());
    }

    return chain.filter(exchange);
  }

  private static ServerHttpRequest addRequestHeaders(ServerWebExchange exchange, String username) {
    return exchange.getRequest().mutate()
            .header("x-user", username.toLowerCase())
            .header("x-user-role", username.toUpperCase())
            .build();
  }
}
