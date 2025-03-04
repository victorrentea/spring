package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class MyFilter implements WebFilter {
  private static final Logger log = LoggerFactory.getLogger(MyFilter.class);

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
    ServerHttpRequest request = exchange.getRequest();
    log.info("request url : {}", request.getURI());
    request.getHeaders().forEach((key, value) -> log.info("{}: {}", key, value));

    exchange.getResponse().getHeaders().add("x-picnic","we rock!");

    return chain.filter(exchange)
        .contextWrite(context -> context.put("username","Gandalf"));
  }
}
