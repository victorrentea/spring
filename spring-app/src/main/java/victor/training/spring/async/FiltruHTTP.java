package victor.training.spring.async;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.stream.Collectors;

@Slf4j
@Component
public class FiltruHTTP extends HttpFilter {
  @Override
  protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
    String s = Collections.list(request.getHeaderNames())
        .stream().map(name -> name + ":" + request.getHeader(name))
        .collect(Collectors.joining("\n"));
    log.info(request.getRequestURI() + " Headere: \n{}", s);
    super.doFilter(request, response, chain);
  }
}
