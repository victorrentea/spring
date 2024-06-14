package com.example.demo;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Component
public class IdempotencyFilter implements Filter {
  public static final Set<String> idempotencyKeys = new HashSet<>();
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest r = (HttpServletRequest) request;
    System.out.println(r.getMethod()+ " " + r.getRequestURI());
    String idempotencyKey = r.getHeader("Idempotency-Key");
    if (idempotencyKey != null) {
      if (idempotencyKeys.contains(idempotencyKey)) {
        throw new RuntimeException("Idempotency-Key already used: " + idempotencyKey);
      }
      idempotencyKeys.add(idempotencyKey);
      // daca + 1 DB call = performanta --
    }
    chain.doFilter( request, response);
  }
}
